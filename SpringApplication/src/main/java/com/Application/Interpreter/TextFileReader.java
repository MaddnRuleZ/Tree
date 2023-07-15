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


    //TODO is tex-File??
    public boolean validateFile() {
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
     *
     * @param inputArray
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static String[] extractStrings(String[] inputArray, int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= inputArray.length || startIndex > endIndex) {
            // Invalid indices, return an empty array or throw an exception
            return new String[0];
        }

        int resultSize = endIndex - startIndex + 1;
        String[] resultArray = new String[resultSize];
        for (int i = startIndex, j = 0; i <= endIndex; i++, j++) {
            resultArray[j] = inputArray[i];
        }

        return resultArray;
    }
}
