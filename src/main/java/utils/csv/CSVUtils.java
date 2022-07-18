package utils.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides facilities to use CSV.
 */
public class CSVUtils {

    /**
     * Reads a csv file and retrieves the info as a matrix
     * The first line is a header with the types and this
     * must match the given header
     * @param f the file to read
     * @param header is the string that must match the initial
     *               line of the file
     * @return String[][] containing all the data
     */
    public static String[][] readCSV(File f, String[] header) {
        if (f == null) {
            throw new IllegalArgumentException("File is null");
        }

        String[][] items = new String[0][];
        try {
            FileReader filereader = new FileReader(f);
            CSVReader csvReader = new CSVReader(filereader);
            String[] initialLine = csvReader.readNext();   //skip the first line which is the header
            if (!Arrays.equals(initialLine, header)) {
                throw new IllegalArgumentException("The given file is invalid for header: " + Arrays.toString(header));
            }

            items = csvReader.readAll().toArray(items);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Takes a path, information and an initial
     * line and writes it in the file that the path
     * indicates
     * @param pathToFile path to the file to write in,
     *                   the file may or may not exist
     * @param data the data to write in the file
     * @param header the initial line to write in the file
     *               as a validation when reading
     */
    public static void writeCSV(String pathToFile, List<String[]> data, String[] header) {
        File file = createCSV(pathToFile);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(header);      //first is validation line
            writer.writeAll(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Takes a path and tries to create a file in that path
     * in case the file can't be created an IllegalStateException is raised
     * otherwise the new created file is returned
     */
    private static File createCSV(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new IllegalStateException("failed to create file at : " + file.getAbsolutePath()
                    + "\n" + ex.toString(), ex);
        }
        return file;
    }

}