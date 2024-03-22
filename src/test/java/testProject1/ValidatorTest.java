package testProject1;

import org.example.project1.TextValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidatorTest {
    ArrayList<String> misspelledWords;
    TextValidator textValidator;

    @BeforeEach
    public void initialize() {
        misspelledWords = new ArrayList<>();
        textValidator = new TextValidator(misspelledWords);
    }

    @Test
    public void performFirstTestCase() {
        String file1 = "src/main/resources/project1Res/textFiles/file1.txt";
        String file2 = "src/main/resources/project1Res/textFiles/file2.txt";

        double expectedPlagiarismPercentage = 95.83;
        double actualPlagiarismPercentage = textValidator.getPlagiarismPercentage(file1, file2);

        assertEquals(expectedPlagiarismPercentage, actualPlagiarismPercentage);

        String expectedMisspelledWords = "printd - printed, informaton - information";
        String actualMisspelledWords = textValidator.printMisspelledWords();

        assertEquals(expectedMisspelledWords, actualMisspelledWords);
    }

    @Test
    public void performSecondTestCase() {
        String file1 = "src/main/resources/project1Res/textFiles/file3.txt";
        String file2 = "src/main/resources/project1Res/textFiles/file4.txt";

        double expectedPlagiarismPercentage = 33.33;
        double actualPlagiarismPercentage = textValidator.getPlagiarismPercentage(file1, file2);

        assertEquals(expectedPlagiarismPercentage, actualPlagiarismPercentage);

        String expectedMisspelledWords = "";
        String actualMisspelledWords = textValidator.printMisspelledWords();

        assertEquals(expectedMisspelledWords, actualMisspelledWords);
    }

    @Test
    public void performThirdTestCase() {
        String file1 = "src/main/resources/project1Res/textFiles/file5.txt";
        String file2 = "src/main/resources/project1Res/textFiles/file6.txt";

        double expectedPlagiarismPercentage = 80.00;
        double actualPlagiarismPercentage = textValidator.getPlagiarismPercentage(file1, file2);

        assertEquals(expectedPlagiarismPercentage, actualPlagiarismPercentage);

        String expectedMisspelledWords = "matematicas - Mathematics";
        String actualMisspelledWords = textValidator.printMisspelledWords();

        assertEquals(expectedMisspelledWords, actualMisspelledWords);
    }
}