package fun.sqlerrorthing.liquidonline.autogenerate.packets.serialization.bytebuf;

import javassist.*;
import javassist.bytecode.SignatureAttribute;

import java.text.MessageFormat;
import java.util.function.Function;

import static fun.sqlerrorthing.liquidonline.autogenerate.packets.serialization.bytebuf.AutoGenerateProcessor.HELPER;

public class Writer {
    public static StringBuilder buildWriteSelfMethodBody(ClassPool pool, CtClass clazz) throws Exception {
        var sb = new StringBuilder();
        sb.append("{");

        createTempWriteObjectMethod(pool, HELPER);

        for (var field : clazz.getDeclaredFields()) {
            writeField(
                    pool,
                    toGetterName(field.getType(), field.getName()) + "()",
                    field.getGenericSignature(),
                    field.getType(),
                    sb,
                    field::hasAnnotation
            );
        }

        deleteTempWriteObjectMethod(pool, HELPER);

        sb.append("}");
        return sb;
    }

    private static void writeField(
            ClassPool pool,
            String name,
            String genericSignature,
            CtClass type,
            StringBuilder sb,
            Function<String, Boolean> hasFieldAnnotation
    ) throws Exception {
        if (type.equals(CtClass.intType) || type.equals(pool.get("java.lang.Integer"))) {
            addWriteStatement(type, generateNumberWriteStatement("Int", hasFieldAnnotation), name, sb);
        } else if (type.equals(CtClass.shortType) || type.equals(pool.get("java.lang.Short"))) {
            addWriteStatement(type, generateNumberWriteStatement("Short", hasFieldAnnotation), name, sb);
        } else if (type.equals(CtClass.byteType) || type.equals(pool.get("java.lang.Byte"))) {
            addWriteStatement(type, "$1.writeByte({0});", name, sb);
        } else if (type.equals(CtClass.booleanType) || type.equals(pool.get("java.lang.Boolean"))) {
            addWriteStatement(type, "$1.writeBoolean({0});", name, sb);
        } else if (type.equals(CtClass.doubleType) || type.equals(pool.get("java.lang.Double"))) {
            addWriteStatement(type, "$1.writeDouble({0});", name, sb);
        } else if (type.equals(CtClass.floatType) || type.equals(pool.get("java.lang.Float"))) {
            addWriteStatement(type, "$1.writeFloat({0});", name, sb);
        } else if (type.equals(CtClass.longType) || type.equals(pool.get("java.lang.Long"))) {
            addWriteStatement(type, generateNumberWriteStatement("Long", hasFieldAnnotation), name, sb);
        } else if (type.equals(pool.get("java.lang.String"))) {
            addWriteStatement(type, "$1.writeString({0});", name, sb);
        } else if (type.isEnum()) {
            addWriteStatement(type, "$1.writeUnsignedVarInt({0}.ordinal());", name, sb);
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
            var signature = SignatureAttribute.toTypeSignature(genericSignature).jvmTypeName();
            var genericType = signature.substring(signature.indexOf('<') + 1, signature.indexOf('>'));
            var generic = pool.get(genericType);

            sb.append("if (%s != null) {".formatted(name));
            sb.append("$1.writeUnsignedVarInt(%s.size());".formatted(name));

            sb.append("for (int i = 0; i < %s.size(); i++) {".formatted(name));
            writeField(pool, "%s.get(i)".formatted(name), genericSignature, generic, sb, (n) -> false);
            sb.append("}");

            sb.append("} else { $1.writeNull(); };");
        } else if (type.isArray()) {
            CtClass componentType = type.getComponentType();
            if (componentType.equals(CtClass.byteType)) {
                sb.append("if (%s != null) {".formatted(name));
                sb.append("$1.writeUnsignedVarInt(%s.length);".formatted(name));
                sb.append("$1.writeBytes(%s);".formatted(name));
                sb.append("} else { $1.writeNull(); }");
            } else {
                throw new UnsupportedOperationException("Unsupported operation yet");
            }
        } else {
            ensureWriteOrReferenceMethod(pool, HELPER, type);

            sb.append("if (%s != null) {".formatted(name));
            sb.append("%s.$write($1, (%s) %s);".formatted(HELPER.getName(), type.getName(), name));
            sb.append("} else { $1.writeNull(); }");
        }
    }

    private static String generateNumberWriteStatement(
            String unit,
            Function<String, Boolean> hasAnnotation
    ) {
        var unsignedAddition = hasAnnotation.apply("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.UnsignedNumber")
                ? "UnsignedVar" : "";
        return "$1.write%s%s({0});".formatted(unsignedAddition, unit);
    }

    private static void addWriteStatement(CtClass type, String statement, String name, StringBuilder sb) {
        statement = MessageFormat.format(statement, name);

        if (type.isPrimitive()) {
            sb.append(statement);
        } else {
            sb.append(" if (%s != null) { %s } else { $1.writeNull(); }".formatted(name, statement));
        }
    }

    private static void createTempWriteObjectMethod(ClassPool pool, CtClass targetClass) throws Exception {
        var bufWriter = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter");
        var obj = pool.get("java.lang.Object");

        try {
            targetClass.getDeclaredMethod("$write", new CtClass[] {bufWriter, obj});
            return;
        } catch (NotFoundException ignored) {}

        if (targetClass.isFrozen()) {
            targetClass.defrost();
        }

        CtMethod newMethod = CtNewMethod.make("""
                public static void $write(%s $1, %s $2) {
                    throw new java.lang.UnsupportedOperationException("Not implemented yet");
                }
                """.formatted(bufWriter.getName(), obj.getName()), targetClass);

        targetClass.addMethod(newMethod);
    }

    private static void deleteTempWriteObjectMethod(ClassPool pool, CtClass targetClass) throws NotFoundException {
        var bufWriter = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter");
        var obj = pool.get("java.lang.Object");

        if (targetClass.isFrozen()) {
            targetClass.defrost();
        }

        targetClass.removeMethod(targetClass.getDeclaredMethod("$write", new CtClass[] {bufWriter, obj}));
    }

    private static void ensureWriteOrReferenceMethod(ClassPool pool, CtClass targetClass, CtClass type) throws Exception {
        var bufWriter = pool.get("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.wrappers.ByteBufWriter");

        try {
            targetClass.getDeclaredMethod("$write", new CtClass[] {bufWriter, type});
            return;
        } catch (NotFoundException ignored) {}

        if (targetClass.isFrozen()) {
            targetClass.defrost();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("public static void $write(%s $1, %s $2) {".formatted(bufWriter.getName(), type.getName()));

        for (var field : type.getDeclaredFields()) {
            writeField(
                    pool,
                    "$2." + toGetterName(field.getType(), field.getName()) + "()",
                    field.getGenericSignature(),
                    field.getType(),
                    sb,
                    field::hasAnnotation
            );
        }

        sb.append("};");
        CtMethod newMethod = CtNewMethod.make(sb.toString(), targetClass);
        targetClass.addMethod(newMethod);
    }


    private static String toGetterName(CtClass fieldType, String fieldName) {
        boolean isBoolean =
                fieldType.equals(CtClass.booleanType) ||
                        fieldType.getName().equals("java.lang.Boolean");

        return (isBoolean ? "is" : "get") + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
