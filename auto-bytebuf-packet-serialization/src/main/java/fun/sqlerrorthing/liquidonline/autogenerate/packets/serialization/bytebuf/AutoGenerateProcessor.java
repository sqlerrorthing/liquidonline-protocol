package fun.sqlerrorthing.liquidonline.autogenerate.packets.serialization.bytebuf;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.*;


public class AutoGenerateProcessor {
    public static void main(String[] args) throws IOException {
        var root = Paths.get(args[0]);
        var pool = ClassPool.getDefault();

        List<Path> classFiles = new ArrayList<>();

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(".class")) {
                    classFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        Map<Path, CtClass> loadedClasses = new HashMap<>();
        for (Path file : classFiles) {
            try {
                byte[] classBytes = Files.readAllBytes(file);
                CtClass ctClass = pool.makeClass(new ByteArrayInputStream(classBytes));
                loadedClasses.put(file, ctClass);
            } catch (IOException e) {
                System.err.println("Failed to read class file: " + file);
                e.printStackTrace();
            }
        }

        // Обрабатываем загруженные классы
        for (Map.Entry<Path, CtClass> entry : loadedClasses.entrySet()) {
            Path file = entry.getKey();
            CtClass clazz = entry.getValue();
            try {
                instrumentClass(pool, clazz);
                try (FileOutputStream fos = new FileOutputStream(file.toFile())) {
                    fos.write(clazz.toBytecode());
                }
            } catch (Exception e) {
                System.err.println("Failed to instrument class: " + clazz.getName());
                e.printStackTrace();
            }
        }
    }

    private static void instrumentClass(ClassPool pool, CtClass clazz) throws Exception {
        try {
            for (CtClass inter : clazz.getInterfaces()) {
                if (inter.getName().equals("fun.sqlerrorthing.liquidonline.packets.Packet")) {
                    instrumentPacketClass(pool, clazz);
                }
            }
        } catch (NotFoundException ignored) {}
    }

    private static void instrumentPacketClass(ClassPool pool, CtClass clazz) throws Exception {
        var serializable = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.PacketSerializable");
        var bufWriter = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter");
        var bufReader = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader");

        clazz.addInterface(serializable);

        CtMethod writeSelf = new CtMethod(CtClass.voidType, "writeSelf", new CtClass[]{bufWriter}, clazz);
        writeSelf.setModifiers(Modifier.PUBLIC);

        {
            var body = buildWriteSelfMethodBody(pool, clazz).toString();
            try {
                writeSelf.setBody(body);
            } catch (CannotCompileException e) {
                System.out.println(body);
                e.printStackTrace();
            }
        }

        CtMethod readSelf = new CtMethod(CtClass.voidType, "readSelf", new CtClass[]{bufReader}, clazz);
        readSelf.setModifiers(Modifier.PUBLIC);
        readSelf.setBody(buildReadSelfMethodBody(pool, clazz).toString());

        clazz.addMethod(writeSelf);
        clazz.addMethod(readSelf);
    }

    private static StringBuilder buildWriteSelfMethodBody(ClassPool pool, CtClass clazz) throws Exception {
        var sb = new StringBuilder();
        sb.append("{");

        for (var field : clazz.getDeclaredFields()) {
            writeField(pool, field.getName(), clazz, field.getType(), sb);
        }

        sb.append("}");
        return sb;
    }

    private static void writeField(ClassPool pool, String name, CtClass root, CtClass type, StringBuilder sb) throws Exception {
        if (type.equals(CtClass.intType) || type.equals(pool.get("java.lang.Integer"))) {
            addWriteStatement(type, "$1.writeInt({0});", name, sb);
        } else if (type.equals(CtClass.shortType) || type.equals(pool.get("java.lang.Short"))) {
            addWriteStatement(type, "$1.writeShort({0});", name, sb);
        } else if (type.equals(CtClass.byteType) || type.equals(pool.get("java.lang.Byte"))) {
            addWriteStatement(type, "$1.writeByte({0});", name, sb);
        } else if (type.equals(CtClass.booleanType) || type.equals(pool.get("java.lang.Boolean"))) {
            addWriteStatement(type, "$1.writeBoolean({0});", name, sb);
        } else if (type.equals(CtClass.doubleType) || type.equals(pool.get("java.lang.Double"))) {
            addWriteStatement(type, "$1.writeDouble({0});", name, sb);
        } else if (type.equals(CtClass.floatType) || type.equals(pool.get("java.lang.Float"))) {
            addWriteStatement(type, "$1.writeFloat({0});", name, sb);
        } else if (type.equals(CtClass.longType) || type.equals(pool.get("java.lang.Long"))) {
            addWriteStatement(type, "$1.writeLong({0});", name, sb);
        } else if (type.equals(pool.get("java.lang.String"))) {
            addWriteStatement(type, "$1.writeString({0});", name, sb);
        } else if (type.isEnum()) {
            addWriteStatement(type, "$1.writeInt({0}.ordinal());", name, sb);
        } else if (type.equals(pool.get("java.awt.Color"))) {
            addWriteStatement(type, """
                    $1.writeByte((byte) {0}.getRed());
                    $1.writeByte((byte) {0}.getGreen());
                    $1.writeByte((byte) {0}.getBlue());
                    """, name, sb);
        } else if (type.equals(pool.get("java.time.Instant"))) {
            addWriteStatement(type, """
                    $1.writeLong({0}.toEpochMilli());
                    """, name, sb);
        } else if (type.equals(pool.get("java.util.UUID"))) {
            addWriteStatement(type, """
                    $1.writeLong({0}.getMostSignificantBits());
                    $1.writeLong({0}.getLeastSignificantBits());
                    """, name, sb);
        } else if (type.equals(pool.get("java.util.List"))) {
            var generic = getFirstGenericType(type);
            assert generic != null;

            sb.append("if (%s != null) {".formatted(name));
            sb.append("$1.writeInt(%s.size());".formatted(name));

            sb.append("for (int i = 0; i < %s.size(); i++) {".formatted(name));
            writeField(pool, "%s.get(i)".formatted(name), root, generic, sb);
            sb.append("}");

            sb.append("} else { $1.writeNull(); };");
        } else {
            ensureWriteOrReferenceMethod(pool, root, type);

            sb.append("if (%s != null) {".formatted(name));
            sb.append("write($1, %s);".formatted(name));
            sb.append("} else { $1.writeNull(); };");
        }
    }

    private static CtClass getFirstGenericType(CtClass ctClass) throws Exception {
        String genericSignature = ctClass.getGenericSignature();
        if (genericSignature == null) {
            return null;
        }

        var classType = SignatureAttribute.toClassSignature(genericSignature).getSuperClass().getTypeArguments()[0];
        System.out.println(classType);
        System.exit(1);
        return ClassPool.getDefault().get("");
    }

    private static void addWriteStatement(CtClass type, String statement, String name, StringBuilder sb) {
        statement = MessageFormat.format(statement, name);

        if (type.isPrimitive()) {
            sb.append(statement);
        } else {
            sb.append(" if (%s != null) { %s } else { $1.writeNull(); };".formatted(name, statement));
        }
    }

    private static void ensureWriteOrReferenceMethod(ClassPool pool, CtClass targetClass, CtClass type) throws Exception {
        String methodName = "write";
        var bufWriter = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter");

        try {
            targetClass.getDeclaredMethod(methodName, new CtClass[] {bufWriter, type});
            return;
        } catch (NotFoundException ignored) {

        }

        StringBuilder sb = new StringBuilder();
        sb.append("private static void write(%s $1, %s $2) {".formatted(bufWriter.getName(), type.getName()));

        for (var field : type.getDeclaredFields()) {
            writeField(pool, "$2." + toGetterName(field.getType(), field.getName()) + "()", targetClass, field.getType(), sb);
        }

        sb.append("};");
        CtMethod newMethod = CtNewMethod.make(sb.toString(), targetClass);
        targetClass.addMethod(newMethod);
    }

    private static StringBuilder buildReadSelfMethodBody(ClassPool pool, CtClass clazz) throws NotFoundException, CannotCompileException {
        var sb = new StringBuilder();
        sb.append("{");

        sb.append("}");
        return sb;
    }

    private static String toGetterName(CtClass fieldType, String fieldName) {

        boolean isBoolean =
                fieldType.equals(CtClass.booleanType) ||
                        fieldType.getName().equals("java.lang.Boolean");

        return (isBoolean ? "is" : "get") + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}