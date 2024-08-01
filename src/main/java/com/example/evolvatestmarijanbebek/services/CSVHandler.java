package com.example.evolvatestmarijanbebek.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVHandler {
    private final String path;

    public CSVHandler(String uploadPath) {
        this.path = uploadPath;
    }

    /**
     * Loads a CSV file and returns the contents as a list of lists of strings.
     * Each value in the CSV is trimmed of leading and trailing whitespace.
     *
     * @param fileName the name of the CSV file to load
     * @return a list of lists of strings representing the contents of the CSV file
     * @throws RuntimeException if an error occurs while reading the CSV file
     */
    public List<List<String>> loadCsv(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                // Trim each value and add to the list
                records.add(
                        Arrays.stream(values).map(String::trim).collect(Collectors.toList())
                );
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    /**
     * Retrieves the names of all files in the upload directory.
     *
     * @return a set of strings representing the names of all files in the upload directory
     * @throws NullPointerException if the directory path is null or the directory does not exist
     */
    public Set<String> getAllFilesInUploadDir() {
        return Stream.of(Objects.requireNonNull(new File(this.path).listFiles()))
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
