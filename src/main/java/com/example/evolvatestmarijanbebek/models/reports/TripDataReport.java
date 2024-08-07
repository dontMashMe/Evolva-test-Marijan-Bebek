package com.example.evolvatestmarijanbebek.models.reports;

import java.util.Map;

public class TripDataReport implements Report{
    private final String countryName;
    private final Map<String, Integer> reportData;

    public TripDataReport(String countryName, Map<String, Integer> reportData) {
        this.countryName = countryName;
        this.reportData = reportData;
    }


    @Override
    public String getFormattedReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(countryName.toLowerCase()).append(".csv\" found\n");
        sb.append("     Totals by currencies:\n");
        for (Map.Entry<String, Integer> entry : reportData.entrySet()) {
            sb.append("         ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
