package com.example.evolvatestmarijanbebek.models.reports;

import java.io.IOException;

/*
* Essentially, the classes which implement this interface are just bundles of (different type) return values
* produced by the SQL queries to the database.
*
* */
public interface Report {
    String getFormattedReport();

    String getHTMLReport() throws IOException;
}
