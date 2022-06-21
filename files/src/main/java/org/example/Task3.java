package org.example;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

//    10 Mb:  1: 30435ms;  2: 16ms;  3: 147ms;   4: 133ms;
//    100 Mb: 1: 304186ms; 2: 16ms;  3: 1252ms;  4: 671ms;
//    1 Gb:   1. ;         2. 16ms;  3: 14007ms; 4: 5987ms;

public class Task3 {

    public static final String FILE_FROM = "files/1gb.txt";
    public static final String DESTINATION_FOLDER = "files/resultFolder";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        copyUsingNIO(Paths.get(FILE_FROM), Paths.get(DESTINATION_FOLDER));
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
    }

    public static void copyUsingFileStream(Path from, Path destinationFolder) {
        String resultFile = excludeErrorScenarios(from, destinationFolder);
        if (resultFile == null) return;

        try (FileInputStream fis = new FileInputStream(from.toFile());
             FileOutputStream fos = new FileOutputStream(resultFile)) {
            int i;
            while ((i = fis.read()) != -1) {
                fos.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyUsingFileStreamWithBuffer(Path from, Path destinationFolder) {
        String resultFile = excludeErrorScenarios(from, destinationFolder);
        if (resultFile == null) return;

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(from.toFile()), 100);
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resultFile))) {
            while (in.available() < 0) {
                byte[] bytes = in.readNBytes(100);
                out.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyUsingFileChannel(Path from, Path destinationFolder) {
        String resultFile = excludeErrorScenarios(from, destinationFolder);
        if (resultFile == null) return;

        try (FileInputStream fin = new FileInputStream(from.toFile());
             FileOutputStream fos = new FileOutputStream(resultFile);
             FileChannel in = fin.getChannel();
             FileChannel out = fos.getChannel()) {

            ByteBuffer buf = ByteBuffer.allocateDirect(100);
            while (in.read(buf) > 0) {
                out.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyUsingNIO(Path from, Path destinationFolder) {
        String resultFile = excludeErrorScenarios(from, destinationFolder);
        if (resultFile == null) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            List<String> collect = Files.lines(from).collect(Collectors.toList());
            writer.write(collect.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String excludeErrorScenarios(Path from, Path destinationFolder) {
        if (!Files.exists(from)) {
            System.out.println("File doesn`t exist");
            return null;
        }
        if (!Files.isDirectory(destinationFolder)) {
            System.out.println("Correct directory does not exists");
            return null;
        }

        return destinationFolder + "\\" + from.getFileName().toString();
    }
}