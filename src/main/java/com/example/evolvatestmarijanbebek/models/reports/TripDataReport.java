package com.example.evolvatestmarijanbebek.models.reports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String getHTMLReport() throws IOException {
        Path templatePath = Path.of("src/main/resources/com/example/evolvatestmarijanbebek/report_template.html");
        Document doc = Jsoup.parse(Files.readString(templatePath));

        Element tableBody = doc.getElementById("table-body");
        assert tableBody != null;

        for (int i = 0; i < countries.size(); i++) {
            String country = countries.get(i);
            Map<String, Integer> currencyData = multipleReportData.get(i);

            int countryRowCounter = 0;
            for (Map.Entry<String, Integer> entry : currencyData.entrySet()) {
                tableBody.append(getTableRow(entry, countryRowCounter, country));
                countryRowCounter++;
            }
        }

        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
        Element reportTime = doc.getElementById("report-date");
        assert reportTime != null;

        reportTime.text("Report for %s".formatted(formattedDateTime));

        return doc.outerHtml();
    }

    private String getTableRow(Map.Entry<String, Integer> entry, int countryRowCounter, String country) {
        if (countryRowCounter == 0) {
            return String.format(
                    """
                            <tr>
                                <td class='coun'>%s</td>
                                <td>%s</td>
                                <td>%d</td>
                            </tr>""",
                    country, entry.getKey(), entry.getValue()
            );
        } else {
            return String.format(
                    """
                            <tr>
                                <td class='coun coun-empty'>%s</td>
                                <td>%s</td>
                                <td>%d</td>
                            </tr>""",
                    "", entry.getKey(), entry.getValue()
            );
        }
    }
}
