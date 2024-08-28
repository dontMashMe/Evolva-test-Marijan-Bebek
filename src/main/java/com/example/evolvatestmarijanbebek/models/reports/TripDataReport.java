package com.example.evolvatestmarijanbebek.models.reports;

import java.util.List;
import java.util.Map;

public class TripDataReport implements Report {
    private String countryName;
    private Map<String, Integer> reportData;

    private List<String> countries;
    private List<Map<String, Integer>> multipleReportData;

    // Constructor for a single country
    public TripDataReport(String countryName, Map<String, Integer> reportData) {
        this.countryName = countryName;
        this.reportData = reportData;
    }

    // Constructor for multiple countries
    public TripDataReport(List<String> countryNames, List<Map<String, Integer>> reportData) {
        this.countries = countryNames;
        this.multipleReportData = reportData;
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

    @Override
    public String getHTMLReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n<head><title>").append(countryName).append(" Report</title></head>\n<body>\n");

        for (int i = 0; i < countries.size(); i++) {
            String country = countries.get(i);
            Map<String, Integer> data = multipleReportData.get(i);

            sb.append("<h1>").append(country).append(" Report</h1>\n");
            sb.append("<h2>Totals by Currencies</h2>\n");
            sb.append("<ul>\n");
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                sb.append("    <li>").append(entry.getKey()).append(": ").append(entry.getValue()).append("</li>\n");
            }
            sb.append("</ul>\n");
        }

        sb.append("</body>\n</html>");
        return sb.toString();
    }
}
