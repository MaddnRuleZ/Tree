package com.application.interpreter;

import com.application.exceptions.FileInvalidException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TextFileReader class for Reading validating and extracting Text from LaTeX documents
 *
 */
public class TextFileReader {
    private final String filePath;
    public TextFileReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Validate the files existence and right Format
     *
     * @return true in case of success
     * @throws FileInvalidException
     */
    public boolean validateFile() throws FileInvalidException {
        File file = new File(filePath);
        String fileName = file.getName();
        if (!file.exists()) {
            throw new FileInvalidException("Datei existiert nicht " + filePath);
        }

        if (file.isDirectory()) {
            throw new FileInvalidException("Pfad ist ein Ordner " + filePath);
        }

        if (!file.canRead()) {
            throw new FileInvalidException("Datei ist nicht Lesbar " + filePath);
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!fileExtension.equalsIgnoreCase("txt") && !fileExtension.equalsIgnoreCase("tex")) {
            throw new FileInvalidException("Datei Endung muss .txt oder .tex sein");
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
     * Remove all leading Space -Characters for later correct indentation
     *
     * @param textLine line to remove Spaces from
     * @return Space cleared Line
     */
    public static String removeSpacesFromStart(String textLine) {
        int index = 0;
        while (index < textLine.length() && (textLine.charAt(index) == ' ')) {
            index++;
        }
        return textLine.substring(index);
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
        resultList.addAll(Arrays.asList(inputArray).subList(startIndex, endIndex + 1));
        return resultList;
    }
}
