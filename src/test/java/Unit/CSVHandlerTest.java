package Unit;

import com.example.evolvatestmarijanbebek.services.CSVHandler;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVHandlerTest {
    private CSVHandler csvHandler;

    @BeforeEach
    public void setUp() {
        csvHandler = new CSVHandler(PathConstants.TestUploadDir.label);
    }

    @Test
    public void testGetAllFilesInUploadDir() {
        Set<String> result = csvHandler.getAllFilesInUploadDir();
        Set<String> expected = new HashSet<>(Arrays.asList("croatia.csv", "germany.csv", "usa.csv", "lobotomized.csv"));

        assertEquals(expected, result, "The set of file names should match the expected names");
    }

    @Test
    public void testLoadCsv() {
        List<List<String>> result = csvHandler.loadCsv(PathConstants.SampleCSVFile.label);
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("Zagreb", "EUR", "1"),
                Arrays.asList("Osijek", "EUR", "2"),
                Arrays.asList("Rijeka", "EUR", "4")
        );

        assertEquals(expected, result, "The CSV data should match the expected data");
    }

}