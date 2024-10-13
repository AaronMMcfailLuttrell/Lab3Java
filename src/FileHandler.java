import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileHandler  {

    public static Map<String, Map<String, Object>> readFile (String fileName) throws IOException {
        //Define map that stores a string value and the record information
        Map<String, Map<String, Object>> entryList = new HashMap<String, Map<String, Object>>();
        Scanner sc = new Scanner(new File(fileName));
        //Iterate to the next line upon first opening, as the first line is the column headers
        sc.nextLine();

        int integerValue;
        double doubleValue;
        char charValue;
        int entryIterateCount = 0;
        while (sc.hasNextLine()) {
            Map<String, Object> lineBuilder = new HashMap<>();
            String line = sc.nextLine();
            String[] fields = line.split("\t");
            int count = 0;
            //Field gets the next word/instance in document

            for (String field : fields) {

                if (isInteger(field)) {
                    integerValue = Integer.parseInt(field);
                    lineBuilder.put(Constants.ColumnLabel[count], integerValue);
                } else if (isDouble(field)) {
                    doubleValue = Double.parseDouble(field);
                    lineBuilder.put(Constants.ColumnLabel[count], doubleValue);
                } else {
                    lineBuilder.put(Constants.ColumnLabel[count], field);
                }
                if (count < Constants.ColumnLabel.length - 1) {
                    count++;
                }
            }
            entryList.put("Entry " + entryIterateCount, lineBuilder);
            entryIterateCount++;
        }
        return entryList;
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isChar(String input) {
        if (input.length() == 1) {
            return true;
        } else {
            return false;
        }
    }

}
