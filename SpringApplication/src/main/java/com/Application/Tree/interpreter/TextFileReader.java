package interpreter;
import java.io.BufferedReader;
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
        return true;
    }


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
