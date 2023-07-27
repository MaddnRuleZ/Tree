package com.application.interpreter;

import com.application.exceptions.FileInvalidException;

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

    //TODO auskommentiert bei E
    public boolean validateFile() throws FileInvalidException {
        File file = new File(filePath);
        /* TODO
        if (!file.exists()) {
            throw new FileInvalidException("Datei existiert nicht " + filePath);
        }
        *
         */
        if (file.isDirectory()) {
            throw new FileInvalidException("Pfad ist ein Ordner " + filePath);
        }
        /*
        if (!file.canRead()) {
            throw new FileInvalidException("Datei ist nicht Lesbar " + filePath);
        }
        *
         */
        String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if (type.equals("tex") ||type.equals("txt")){ //TODO hinzugefügt
            throw new FileInvalidException("Datei endet nicht auf .tex " + filePath);
        }
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
            throw new IllegalArgumentException("Index out of Bounds for extracting Strings from Array");
        }

        List<String> resultList = new ArrayList<>(endIndex - startIndex + 1);
        for (int i = startIndex; i <= endIndex; i++) {
            resultList.add(inputArray[i]);
        }
        return resultList;
    }
}
