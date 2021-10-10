package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileService {
    public static void createFile(String path, String name) {
        File file = new File(path + File.separator + name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(String name) {
        File file = new File(name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeInFile(String path, String name, String text) {
        try {
            Files.write(Paths.get(path + File.separator + name), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeInFile(String name, String text) {
        try {
            Files.write(Paths.get(name), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFromFile(String path, String name) throws IOException {
        return Files.readAllLines(Paths.get(path + File.separator + name));
    }

    public static List<String> readFromFile(String name) throws IOException {
        return Files.readAllLines(Paths.get(name));
    }

    public static void showFile(String name) {
        List<String> data = null;
        try {
            data = readFromFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data != null && data.size() > 2) {
            for (int i = 2; i < data.size(); i++) {
                System.out.println(data.get(i));
            }
        }
    }
}
