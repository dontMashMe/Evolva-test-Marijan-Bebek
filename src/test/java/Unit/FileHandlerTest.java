package Unit;

import com.example.evolvatestmarijanbebek.services.fileHandling.FileHandler;
import com.example.evolvatestmarijanbebek.utils.PathConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileHandlerTest {
    private FileHandler fileHandler;

    @BeforeEach
    public void setUp() {
        fileHandler = new FileHandler(PathConstants.TestUploadDir.label);
    }

    @Test
    public void testGetAllFilesInUploadDir() {
        Set<String> result = fileHandler.getAllFilesInUploadDir();
        Set<String> expected = new HashSet<>(Arrays.asList("croatia.csv", "germany.csv", "usa.csv", "lobotomized.csv"));

        assertEquals(expected, result, "The set of file names should match the expected names");
    }

    @Test
    public void testLoadCsv() {
        List<List<String>> result = fileHandler.loadCsv(PathConstants.SampleCSVFile.label);
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("Zagreb", "EUR", "1"),
                Arrays.asList("Osijek", "EUR", "2"),
                Arrays.asList("Rijeka", "EUR", "4"),
                Arrays.asList("Split", "USD", "5"),
                Arrays.asList("Kutjevo", "HRK", "15")
        );

        assertEquals(expected, result, "The CSV data should match the expected data");
    }

    @Test
    public void testLoadCSVWithEmptySpaces() {
        List<List<String>> result = fileHandler.loadCsv("germany.csv");
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("Berlin", "EUR", "10"),
                Arrays.asList("Muenchen", "EUR", "20")
        );
    }

}
