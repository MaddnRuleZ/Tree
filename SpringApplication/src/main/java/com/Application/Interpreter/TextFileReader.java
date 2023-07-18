package com.Application.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {
    private final String filePath;
    public TextFileReader(String filePath) {
        this.filePath = filePath;
    }

    public boolean validateFile() {
        //TODO is tex-File??
        File file = new File(filePath);

        // Check if file exists
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return false;
        }

        // Check if file is a directory
        if (file.isDirectory()) {
            System.out.println("File is a directory: " + filePath);
            return false;
        }

        // Check if file is readable
        if (!file.canRead()) {
            System.out.println("File is not readable: " + filePath);
            return false;
        }

        String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if(type.equals("tex")){
            System.out.println("File is not readable: " + filePath);
            return false;
        }

        // File exists, is not a directory, and is readable
        return true;
    }


    /**
     * read the File from the given FilePath and return the Text
     * Line by Line as String Array
     *
     * @return textFile as Array
     */
    public String[] readLinesFromFile() {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.toArray(new String[0]);
    }

    /**
     * Extracts a portion of an array of strings.
     *
     * @param inputArray   the input array of strings
     * @param startIndex   the starting index (inclusive)
     * @param endIndex     the ending index (inclusive)
     * @return             a list of strings containing the extracted portion
     */
    public static List<String> extractStrings(String[] inputArray, int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= inputArray.length || startIndex > endIndex) {
            // Invalid indices, return an empty list or throw an exception
            return new ArrayList<>();
        }

        List<String> resultList = new ArrayList<>(endIndex - startIndex + 1);
        for (int i = startIndex; i <= endIndex; i++) {
            resultList.add(inputArray[i]);
        }
        return resultList;
    }
}
