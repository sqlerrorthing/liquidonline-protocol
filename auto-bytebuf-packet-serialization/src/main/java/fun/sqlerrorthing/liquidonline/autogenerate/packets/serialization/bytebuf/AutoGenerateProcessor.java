package fun.sqlerrorthing.liquidonline.autogenerate.packets.serialization.bytebuf;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoGenerateProcessor {
    public static final String DESERIALIZATION_HELPER_CLASS = "fun.sqlerrorthing.liquidonline.packets._ClassesPacketSerialization";
    public static Path BUILD_DIR;
    public static CtClass HELPER;

    public static void main(String[] args) throws IOException, CannotCompileException {
        var root = Paths.get(args[0]);
        BUILD_DIR = root;

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

        try {
            HELPER = pool.get(DESERIALIZATION_HELPER_CLASS);
        } catch (NotFoundException e) {
            HELPER = pool.makeClass(DESERIALIZATION_HELPER_CLASS);
        }

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
                System.exit(1);
            }
        }

        HELPER.writeFile(BUILD_DIR.toAbsolutePath().toString());
    }

    private static void instrumentClass(ClassPool pool, CtClass clazz) throws Exception {
        try {
            boolean implementsPacket = false;
            for (CtClass inter : clazz.getInterfaces()) {
                if (inter.getName().equals("fun.sqlerrorthing.liquidonline.packets.Packet")) {
                    implementsPacket = true;
                    break;
                }
            }

            if (!implementsPacket) {
                return;
            }

            boolean implementsPacketSerializable = false;
            for (CtClass inter : clazz.getInterfaces()) {
                if (inter.getName().equals("fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.PacketSerializable")) {
                    implementsPacketSerializable = true;
                    break;
                }
            }

            if (implementsPacketSerializable) {
                return;
            }

            instrumentPacketClass(pool, clazz);
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
            var body = Writer.buildWriteSelfMethodBody(pool, clazz).toString();
            try {
                writeSelf.setBody(body);
            } catch (CannotCompileException e) {
                System.out.println(body);
                e.printStackTrace();
            }
        }

        CtMethod readSelf = new CtMethod(CtClass.voidType, "readSelf", new CtClass[]{bufReader}, clazz);
        readSelf.setModifiers(Modifier.PUBLIC);

        {
            var body = Reader.buildReadSelfMethodBody(pool, clazz).toString();
            try {
                readSelf.setBody(body);
            } catch (CannotCompileException e) {
                System.out.println(body);
                e.printStackTrace();
            }
        }

        clazz.addMethod(writeSelf);
        clazz.addMethod(readSelf);
    }
}