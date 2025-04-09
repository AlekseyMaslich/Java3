package task02.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task01.CalculationData;
import task02.ViewResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code TestMain} class contains unit tests for the functionality
 * of the {@code ViewResult} class, including its initialization, calculations,
 * saving, restoring, and output display.
 */
public class TestMain {

    /** The {@code ViewResult} instance used in the tests. */
    private ViewResult viewResult;

    /**
     * Initializes the {@code ViewResult} instance before each test, creating 5 random items
     * and calculating their results.
     */
    @BeforeEach
    void setUp() {
        viewResult = new ViewResult(5); // create 5 random items
        viewResult.viewCalculate();
    }

    /**
     * Tests the initialization of {@code ViewResult} with different sizes.
     * It ensures that the number of items created matches the specified size.
     */
    @Test
    void testInitializationWithDifferentSizes() {
        ViewResult result1 = new ViewResult(3);
        ViewResult result2 = new ViewResult(7);

        assertEquals(3, result1.getItems().size());
        assertEquals(7, result2.getItems().size());
    }

    /**
     * Tests the {@code viewInit()} method of {@code ViewResult}, which generates new random data.
     * It checks that the number of items remains the same, but the numbers are different from the previous data.
     */
    @Test
    void testViewInitCreatesNewData() {
        ArrayList<CalculationData> originalItems = new ArrayList<>(viewResult.getItems());
        viewResult.viewInit();

        assertEquals(originalItems.size(), viewResult.getItems().size());
        assertNotEquals(originalItems.get(0).getNumber(), viewResult.getItems().get(0).getNumber());
    }

    /**
     * Tests that the calculations for each item in {@code ViewResult} are correct.
     * It ensures that the binary, octal, and hexadecimal values match the expected results.
     */
    @Test
    void testCalculationsAreCorrect() {
        for (CalculationData item : viewResult.getItems()) {
            int number = item.getNumber();
            assertEquals(Integer.toBinaryString(number), item.getBinary());
            assertEquals(Integer.toOctalString(number), item.getOctal());
            assertEquals(Integer.toHexString(number).toUpperCase(), item.getHexadecimal());
        }
    }

    /**
     * Tests the save and restore functionality of {@code ViewResult}.
     * It ensures that the saved data is correctly restored and that the content remains the same.
     *
     * @throws Exception if an error occurs during saving or restoring
     */
    @Test
    void testSaveAndRestore() throws Exception {
        viewResult.viewSave();
        File savedFile = new File("data.ser");
        assertTrue(savedFile.exists());

        ViewResult restoredResult = new ViewResult(0);
        restoredResult.viewRestore();

        assertEquals(viewResult.getItems().size(), restoredResult.getItems().size());

        for (int i = 0; i < viewResult.getItems().size(); i++) {
            CalculationData original = viewResult.getItems().get(i);
            CalculationData restored = restoredResult.getItems().get(i);

            assertEquals(original.getNumber(), restored.getNumber());
            assertEquals(original.getBinary(), restored.getBinary());
            assertEquals(original.getOctal(), restored.getOctal());
            assertEquals(original.getHexadecimal(), restored.getHexadecimal());
        }
    }

    /**
     * Tests the {@code show()} method of {@code ViewResult} to ensure it correctly outputs the results
     * to the console. It compares the actual output to the expected format.
     */
    @Test
    void testShowOutputsResults() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        viewResult.show();

        System.setOut(originalOut);
        String actualOutput = outputStream.toString();

        // Manually build the expected output
        StringBuilder expected = new StringBuilder();

        for (CalculationData item : viewResult.getItems()) {
            expected.append("Number: ")
                    .append(item.getNumber())
                    .append("\tBinary: ")
                    .append(item.getBinary())
                    .append("\tOctal: ")
                    .append(item.getOctal())
                    .append("\tHexadecimal: ")
                    .append(item.getHexadecimal())
                    .append("\r\n");
        }

        assertTrue(actualOutput.contains(expected.toString()));
    }
}
