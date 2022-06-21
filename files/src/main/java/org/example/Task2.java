package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task2 {

    public static final String RESULT_FILE = "files/result.txt";

    public static void main(String[] args) {

        System.out.println("Enter functionality number:\n" +
                "1. Find filename with most frequent letter 's'\n" +
                "2. Output average size of files\n" +
                "3. Output five largest files\n" +
                "4. Output amount of files and folders with selected letter");

        Scanner scanner = new Scanner(System.in);
        int numOfFunction = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter path:");
        String path = scanner.nextLine();

        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            System.out.println("Invalid path");
            return;
        }
        try {
            switch (numOfFunction) {
                case (1): {
                    findFileWithMoreFrequentSLetter(path);
                    break;
                }
                case 2: {
                    printFiveLargestFiles(path);
                    break;
                }
                case 3: {
                    getAverageFileSizeInDirectory(path);
                    break;
                }
                case 4: {
                    System.out.println("Enter letter:");
                    String letter = scanner.nextLine();
                    numsOfFilesDivByALetter(path, letter);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void findFileWithMoreFrequentSLetter(String path) throws IOException {
        List<Path> pathsOfFiles = getPathsOfFiles(path);

        List<List<Character>> namesOfFiles = pathsOfFiles.stream()
                .filter(Files::isRegularFile)
                .map(pathOfFile -> pathOfFile
                        .getFileName()
                        .toString()
                        .chars()
                        .mapToObj(ch -> (char) ch)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        String res = String.valueOf(namesOfFiles.stream().max(Comparator.comparing(name ->
                        Collections.frequency(name, 's')))
                .orElseThrow());

        System.out.println(res);
        addResultToFile(res);
    }

    private static List<Path> getPathsOfFiles(String path) throws IOException {
        return Files.walk(Path.of(path)).collect(Collectors.toList());
    }

    private static void printFiveLargestFiles(String path) throws IOException {
        List<Path> pathsOfFiles = getPathsOfFiles(path);

        List<String> listOfFive = pathsOfFiles.stream()
                .filter(Files::isRegularFile)
                .sorted(Comparator.comparing((pathOfFile) -> {
                    try {
                        return Files.size(pathOfFile);
                    } catch (IOException e) {
                        return 0L;
                    }
                }))
                .map(pathOfFile -> pathOfFile.getFileName().toString())
                .collect(Collectors.toList());

        Collections.reverse(listOfFive);

        if (listOfFive.size() > 5) {
            listOfFive = listOfFive.subList(0, 5);
        }

        System.out.println(listOfFive);
        addResultToFile(listOfFive.toString());
    }

    private static void getAverageFileSizeInDirectory(String path) throws IOException {
        List<Path> pathList = getPathsOfFiles(path);

        double result = pathList.stream()
                .filter(Files::isRegularFile)
                .mapToDouble(filePath -> {
                    try {
                        return Files.size(filePath);
                    } catch (IOException e) {
                        return 0L;
                    }
                })
                .average()
                .orElse(0.0d);

        System.out.println(result);
        addResultToFile(String.valueOf(result));
    }

    private static void numsOfFilesDivByALetter(String path, String letter) throws IOException {
        List<Path> paths = getPathsOfFiles(path);

        long numsOfFolders = paths.stream()
                .filter(Files::isDirectory)
                .filter(directory -> directory.getFileName().toString().startsWith(letter))
                .count();

        long numsOfFiles = paths.stream()
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().toString().startsWith(letter))
                .count();
        String res = "Folders: " + numsOfFolders + "; Files: " + numsOfFiles;
        System.out.println(res);
        addResultToFile(res);
    }

    private static void addResultToFile(String result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE))) {
            writer.write(result);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
