package com.example.evolvatestmarijanbebek.services;

import com.example.evolvatestmarijanbebek.utils.PathConstants;
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
    public List<List<String>> loadCsv(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public Set<String> getAllFilesInUploadDir() {
        return Stream.of(Objects.requireNonNull(new File(this.path).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
