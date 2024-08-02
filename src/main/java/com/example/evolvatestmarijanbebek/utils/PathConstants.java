package com.example.evolvatestmarijanbebek.utils;

public enum PathConstants {
    /*Application paths*/
    DBProperties("src/main/resources/com/example/evolvatestmarijanbebek/db.properties"),
    UploadDir("uploads"),


    /*Test paths*/
    TestUploadDir("src/test/resources/uploads"),
    SampleCSVFile("src/test/resources/uploads/lobotomized.csv");

    public final String label;

    PathConstants(String s) {
        this.label = s;
    }
}
