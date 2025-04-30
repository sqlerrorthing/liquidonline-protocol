package fun.sqlerrorthing.liquidonline.autogenerate.packets.serialization.bytebuf;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;

import java.text.MessageFormat;

import static fun.sqlerrorthing.liquidonline.autogenerate.packets.serialization.bytebuf.AutoGenerateProcessor.HELPER;

public class Reader {
    public static StringBuilder buildReadSelfMethodBody(ClassPool pool, CtClass clazz) throws NotFoundException, CannotCompileException, BadBytecode {
        var sb = new StringBuilder();
        sb.append("{");

        createTempReadObjectMethod(pool, HELPER);

        for (var field : clazz.getDeclaredFields()) {
            readField(pool, toSetterName(field.getName()), field.getGenericSignature(), field.getType(), sb);
        }

        deleteTempReadObjectMethod(pool, HELPER);

        sb.append("}");
        return sb;
    }

    private static void readField(ClassPool pool, String nameWithSetterSyntax, String genericSignature, CtClass type, StringBuilder sb) throws NotFoundException, CannotCompileException, BadBytecode {
        if (type.equals(CtClass.intType) || type.equals(pool.get("java.lang.Integer"))) {
            var state = type.equals(pool.get("java.lang.Integer")) ? "java.lang.Integer.valueOf($1.readInt())" : "$1.readInt()";
            addCheckedReadStatement(type, "{0}(%s);".formatted(state), nameWithSetterSyntax, sb);
        } else if (type.equals(CtClass.shortType) || type.equals(pool.get("java.lang.Short"))) {
            addCheckedReadStatement(type, "{0}($1.readShort());", nameWithSetterSyntax, sb);
        } else if (type.equals(CtClass.byteType) || type.equals(pool.get("java.lang.Byte"))) {
            addCheckedReadStatement(type, "{0}($1.readByte());", nameWithSetterSyntax, sb);
        } else if (type.equals(CtClass.booleanType) || type.equals(pool.get("java.lang.Boolean"))) {
            addCheckedReadStatement(type, "{0}($1.readBoolean());", nameWithSetterSyntax, sb);
        } else if (type.equals(CtClass.doubleType) || type.equals(pool.get("java.lang.Double"))) {
            addCheckedReadStatement(type, "{0}($1.readDouble());", nameWithSetterSyntax, sb);
        } else if (type.equals(CtClass.floatType) || type.equals(pool.get("java.lang.Float"))) {
            addCheckedReadStatement(type, "{0}($1.readFloat());", nameWithSetterSyntax, sb);
        } else if (type.equals(CtClass.longType) || type.equals(pool.get("java.lang.Long"))) {
            addCheckedReadStatement(type, "{0}($1.readLong());", nameWithSetterSyntax, sb);
        } else if (type.equals(pool.get("java.lang.String"))) {
            addCheckedReadStatement(type, "{0}($1.readString());", nameWithSetterSyntax, sb);
        } else if (type.isEnum()) {
            addCheckedReadStatement(type, "{0}(%s.values()[$1.readInt()]);".formatted(type.getName()), nameWithSetterSyntax, sb);
        } else if (type.equals(pool.get("java.awt.Color"))) {
            addCheckedReadStatement(type, """
                    {0}(new java.awt.Color(
                        (int) $1.readUnsignedByte(),
                        (int) $1.readUnsignedByte(),
                        (int) $1.readUnsignedByte()
                    ));
                    """, nameWithSetterSyntax, sb);
        } else if (type.equals(pool.get("java.time.Instant"))) {
            addCheckedReadStatement(type, """
                    {0}(java.time.Instant.ofEpochMilli($1.readLong()));
                    """, nameWithSetterSyntax, sb);
        } else if (type.equals(pool.get("java.util.UUID"))) {
            addCheckedReadStatement(type, """
                    {0}(new java.util.UUID(
                        $1.readLong(),
                        $1.readLong()
                    ));
                    """, nameWithSetterSyntax, sb);
        } else if (type.isArray()) {
            CtClass component = type.getComponentType();
            if (component.equals(CtClass.byteType)) {
                addCheckedReadStatement(type, """
                        byte[] generated = new byte[$1.readInt()];
                        $1.readBytes(generated);
                        {0}(generated);
                        """, nameWithSetterSyntax, sb);
            } else {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        } else if (type.equals(pool.get("java.util.List"))) {
            var signature = SignatureAttribute.toTypeSignature(genericSignature).jvmTypeName();
            var genericType = signature.substring(signature.indexOf('<') + 1, signature.indexOf('>'));
            var generic = pool.get(genericType);

            addCheckedReadStatement(type, () -> {
                sb.append(
                        """
                        int size = $1.readInt();
                        java.util.List list = new java.util.ArrayList(size);
                        
                        for (int i = 0; i < size; i++) {
                        """
                );

                try {
                    readField(pool, "list.add", generic.getGenericSignature(), generic, sb);
                } catch (NotFoundException | CannotCompileException | BadBytecode e) {
                    throw new RuntimeException(e);
                }

                sb.append(
                        """
                        }
                        
                        %s(list);
                        """.formatted(nameWithSetterSyntax)
                );

            }, nameWithSetterSyntax, sb);
        } else {
            ensureReadOrReferenceMethod(pool, HELPER, type);

            addCheckedReadStatement(type,"""
                    %s generated = new %s();
                    %s.$read($1, generated);
                    {0}(generated);
                    """.formatted(type.getName(), type.getName(), HELPER.getName()), nameWithSetterSyntax, sb);
        }
    }

    private static void addCheckedReadStatement(CtClass type, Runnable statement, String name, StringBuilder sb) {
        if (!type.isPrimitive()) {
            sb.append("""
                    if ($1.peekIsNullMarker()) {
                        $1.skipByte();
                        %s(null);
                    } else {
                    """.formatted(name));

            statement.run();

            sb.append("}");
        } else {
            statement.run();
        }
    }

    private static void ensureReadOrReferenceMethod(ClassPool pool, CtClass targetClass, CtClass type) throws NotFoundException, CannotCompileException, BadBytecode {
        var bufReader = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader");

        try {
            targetClass.getDeclaredMethod("$read", new CtClass[] {bufReader, type});
            return;
        } catch (NotFoundException ignored) {}

        StringBuilder sb = new StringBuilder();
        sb.append("public static void $read(%s $1, %s $2) {".formatted(bufReader.getName(), type.getName()));

        for (var field : type.getDeclaredFields()) {
            readField(pool, "$2." + toSetterName(field.getName()), field.getGenericSignature(), field.getType(), sb);
        }

        sb.append("};");
        CtMethod newMethod = CtNewMethod.make(sb.toString(), targetClass);
        targetClass.addMethod(newMethod);
    }

    private static void addCheckedReadStatement(CtClass type, String statement, String name, StringBuilder sb) {
        addCheckedReadStatement(type, () -> sb.append(MessageFormat.format(statement, name)), name, sb);
    }


    private static void deleteTempReadObjectMethod(ClassPool pool, CtClass targetClass) throws NotFoundException {
        var bufWriter = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader");
        var obj = pool.get("java.lang.Object");

        targetClass.removeMethod(targetClass.getDeclaredMethod("$read", new CtClass[] {bufWriter, obj}));
    }

    private static void createTempReadObjectMethod(ClassPool pool, CtClass targetClass) throws NotFoundException, CannotCompileException {
        var bufReader = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufReader");
        var obj = pool.get("java.lang.Object");


        try {
            targetClass.getDeclaredMethod("$read", new CtClass[] {bufReader, obj});
            return;
        } catch (NotFoundException ignored) {}


        CtMethod newMethod = CtNewMethod.make("""
                public static void $read(%s $1, %s $2) {
                    throw new java.lang.UnsupportedOperationException("Not implemented yet");
                }
                """.formatted(bufReader.getName(), obj.getName()), targetClass);

        targetClass.addMethod(newMethod);
    }

    private static String toSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
