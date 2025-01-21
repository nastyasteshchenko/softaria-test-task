package test.softaria.options;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineParserTest {
    private final static String YESTERDAY_PAGES_FILE_NAME = "yesterday-pages.json";
    private final static String TODAY_FILE_NAME = "today-pages.json";
    private final static String RECEIVER_FIRST_NAME = "Екатерина";
    private final static String RECEIVER_PATRONYMIC_NAME = "Викторовна";
    private final static String RECEIVER_NAME = RECEIVER_FIRST_NAME + " " + RECEIVER_PATRONYMIC_NAME;
    private final CommandLineParser commandLineParser = new CommandLineParser();
    @TempDir
    private static Path inputDir;
    private static Path yesterdayPagesFile;
    private static Path todayPagesFile;

    @BeforeAll
    public static void setup() throws IOException {
        yesterdayPagesFile = inputDir.resolve(YESTERDAY_PAGES_FILE_NAME);
        Files.createFile(yesterdayPagesFile);
        todayPagesFile = inputDir.resolve(TODAY_FILE_NAME);
        Files.createFile(todayPagesFile);
    }

    @Test
    public void testEmptyArgs() {
        assertThrows(ParseException.class, () -> commandLineParser.parse(new String[]{}));
    }

    @Test
    public void testNoRequiredOption() {
        assertThrows(ParseException.class,
                () -> commandLineParser.parse(
                        new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), YESTERDAY_PAGES_FILE_NAME}));
    }

    @Test
    public void testSuccessSetOptions() throws OptionsValuesException, ParseException {
        OptionsValues optionsValues = commandLineParser.parse(
                new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), yesterdayPagesFile.toString(),
                        getCLOptionName(OptionType.TODAY_PAGES_FILE), todayPagesFile.toString(),
                        getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME, RECEIVER_PATRONYMIC_NAME});

        assertEquals(yesterdayPagesFile.toString(), optionsValues.yesterdayPagesFile());
        assertEquals(todayPagesFile.toString(), optionsValues.todayPagesFile());
        assertEquals(RECEIVER_NAME, optionsValues.letterReceiverName());
    }

    @Test
    public void testDuplicatedOptionFile() {
        Throwable thrown = assertThrows(OptionsValuesException.class, () ->
                commandLineParser.parse(
                        new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), yesterdayPagesFile.toString(),
                                getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), yesterdayPagesFile.toString(),
                                getCLOptionName(OptionType.TODAY_PAGES_FILE), todayPagesFile.toString(),
                                getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME,
                                RECEIVER_PATRONYMIC_NAME}));

        assertEquals("Duplicated option 'y'.", thrown.getMessage());

    }

    @Test
    public void testNoArgumentForSetOutputFileOption() {
        assertThrows(ParseException.class, () -> commandLineParser.parse(
                new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), yesterdayPagesFile.toString(),
                        getCLOptionName(OptionType.TODAY_PAGES_FILE),
                        getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME, RECEIVER_PATRONYMIC_NAME}));
    }

    @Test
    public void testLessArgumentsForSetReceiverNameOption() {
        assertThrows(ParseException.class, () -> commandLineParser.parse(
                new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), yesterdayPagesFile.toString(),
                        getCLOptionName(OptionType.TODAY_PAGES_FILE), todayPagesFile.toString(),
                        getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME}));
    }

    @Test
    public void testNotExistingDirectory() {
        String notExistingFile = inputDir.resolve("i").toString();
        Throwable thrown = assertThrows(OptionsValuesException.class, () ->
                commandLineParser.parse(
                        new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), yesterdayPagesFile.toString(),
                                getCLOptionName(OptionType.TODAY_PAGES_FILE), notExistingFile,
                                getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME,
                                RECEIVER_PATRONYMIC_NAME}));

        assertEquals(String.format("Cannot access '%s': No such file or directory.", notExistingFile),
                thrown.getMessage());
    }

    @Test
    public void testInputFileIsDirectory() {
        Throwable thrown = assertThrows(OptionsValuesException.class, () ->
                commandLineParser.parse(
                        new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), inputDir.toString(),
                                getCLOptionName(OptionType.TODAY_PAGES_FILE), todayPagesFile.toString(),
                                getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME,
                                RECEIVER_PATRONYMIC_NAME}));

        assertEquals(String.format("Cannot read from '%s'.", inputDir), thrown.getMessage());
    }

    @Test
    public void testNotExistingOption() {
        assertThrows(ParseException.class,
                () -> commandLineParser.parse(
                        new String[]{getCLOptionName(OptionType.YESTERDAY_PAGES_FILE), inputDir.toString(),
                                getCLOptionName(OptionType.TODAY_PAGES_FILE), todayPagesFile.toString(),
                                getCLOptionName(OptionType.RECEIVER_NAME), RECEIVER_FIRST_NAME,
                                RECEIVER_PATRONYMIC_NAME, "-r", "r.txt"}));
    }

    private String getCLOptionName(OptionType optionType) {
        return "-" + optionType.getShortName();
    }
}