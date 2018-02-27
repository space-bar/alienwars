package com.spacebar.alienwars.util;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Properties;

public final class FileUtils {

    public static final String FILE_DIR = "java.io.tmpdir";

    private static String getHomeDirectory() {
        return System.getProperty(FILE_DIR) + File.separator + "alien-wars";
    }

    private FileUtils() {
    }

    public static Properties readManifest() throws IOException {
        Path path = Paths.get(getHomeDirectory(), "manifest");
        Properties properties = new Properties();
        if (path.toFile().exists()) {
            try (InputStream inputStream = Files.newInputStream(path)) {
                properties.load(inputStream);
            }
        }
        return properties;
    }

    public static void writeManifest(Properties properties) throws IOException {
        Path path = Paths.get(getHomeDirectory(), "manifest");
        ensureDirectories(path.getParent());
        try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE)) {
            properties.store(outputStream, String.valueOf(System.currentTimeMillis()));
        }
    }

    public static <T extends Serializable> T readAsObject(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(getHomeDirectory(), fileName);
        if (path.toFile().exists()) {
            byte[] bytes = Files.readAllBytes(path);
            return deserializeObject(bytes);
        }
        return null;
    }

    public static <T extends Serializable> void writeAsObject(String fileName, T object) throws IOException {
        byte[] serializedObject = serializeObject(object);
        Path path = Paths.get(getHomeDirectory(), fileName);
        ensureDirectories(path.getParent());
        Files.write(path, serializedObject, StandardOpenOption.CREATE);
    }

    private static <T extends Serializable> byte[] serializeObject(T object) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream)) {
            oos.writeObject(object);
            return Base64.getEncoder().encode(byteArrayOutputStream.toByteArray());
        }
    }

    private static <T extends Serializable> T deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
        bytes = Base64.getDecoder().decode(bytes);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream)) {
            return (T) ois.readObject();
        }
    }

    private static void ensureDirectories(Path dir) throws IOException {
        if (!dir.toFile().exists()) {
            Files.createDirectories(dir);
        }
    }
}
