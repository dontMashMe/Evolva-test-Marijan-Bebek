package com.example.evolvatestmarijanbebek.utils;

public enum PathConstants {
    /*Application paths*/
    DBProperties("src/main/resources/com/example/evolvatestmarijanbebek/db.properties"),
    UploadDir("uploads"),


    /*Test paths*/
    TestUploadDir("src/test/resources/uploads"),
    SampleCSVFile("lobotomized.csv"),
    InitTestDatabaseScript("src/test/resources/init.sql"),
    CleanupTestDatabaseScript("src/test/resources/cleanup.sql");

    public String label;

    PathConstants(String s) {
        this.label = s;
    }
}
