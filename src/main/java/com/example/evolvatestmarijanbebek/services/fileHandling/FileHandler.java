package com.example.evolvatestmarijanbebek.services.fileHandling;

import com.example.evolvatestmarijanbebek.utils.PathConstants;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class FileHandler {
    private final String path;

    public FileHandler(String uploadPath) {
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
        try (CSVReader csvReader = new CSVReader(new FileReader(String.valueOf(Paths.get(this.path, fileName))))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                // Trim each value and check if the row is empty or contains only whitespace
                List<String> trimmedValues = Arrays.stream(values)
                        .map(value -> value == null ? "" : value.trim())
                        .collect(Collectors.toList());

                // Check if the row is empty or contains only whitespace
                if (trimmedValues.stream().allMatch(String::isEmpty)) {
                    continue; // Skip this row
                }

                records.add(trimmedValues);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("Error reading CSV file: " + fileName, e);
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
        File[] files = new File(this.path).listFiles();
        if (files == null) return Collections.emptySet();

        Set<String> fileNames = new HashSet<>();
        for (File file: files) {
            if(file.getName().contains("csv"))
                fileNames.add(file.getName());
        }
        return fileNames;
    }

    public static void copyFileToUploadDir(File file) {
        try {
            Path destination = Paths.get(PathConstants.UploadDir.label, file.getName());
            Files.copy(file.toPath(), destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
