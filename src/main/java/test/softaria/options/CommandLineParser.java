package test.softaria.options;

import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class CommandLineParser {
    private final Options options;

    public CommandLineParser() {
        options = new Options();
        for (OptionType optionType : OptionType.values()) {
            options.addOption(createOption(optionType));
        }
    }

    public OptionsValues parse(String[] args) throws ParseException, OptionsValuesException {
        CommandLine commandLine = new DefaultParser().parse(options, args);

        String[] yesterdayPagesFileValues = commandLine.getOptionValues(OptionType.YESTERDAY_PAGES_FILE.getShortName());
        if (yesterdayPagesFileValues.length > 1) {
            throw OptionsValuesException.duplicateOption(OptionType.YESTERDAY_PAGES_FILE.getShortName());
        }
        String yesterdayPagesFile = yesterdayPagesFileValues[0];
        checkIfFileExists(yesterdayPagesFile);
        checkIfRegularFile(yesterdayPagesFile);

        String[] todayPagesFileValues = commandLine.getOptionValues(OptionType.TODAY_PAGES_FILE.getShortName());
        if (todayPagesFileValues.length > 1) {
            throw OptionsValuesException.duplicateOption(OptionType.TODAY_PAGES_FILE.getShortName());
        }
        String todayPagesFile = todayPagesFileValues[0];
        checkIfFileExists(todayPagesFile);
        checkIfRegularFile(todayPagesFile);

        String[] receiverName = commandLine.getOptionValues(OptionType.RECEIVER_NAME.getShortName());
        if (receiverName.length > 2) {
            throw OptionsValuesException.duplicateOption(OptionType.RECEIVER_NAME.getShortName());
        }

        String name = receiverName[0] + " " + receiverName[1];
        return new OptionsValues(yesterdayPagesFile, todayPagesFile, name);
    }

    private Option createOption(OptionType optionType) {
        return Option.builder(optionType.getShortName())
                .argName(optionType.getArgName())
                .desc(optionType.getDescription())
                .hasArg()
                .numberOfArgs(optionType.getArgsNumber())
                .required(true)
                .optionalArg(false)
                .build();
    }

    private void checkIfRegularFile(String inputPath) throws OptionsValuesException {
        if (!Files.isRegularFile(Path.of(inputPath))) {
            throw OptionsValuesException.notReadableFile(inputPath);
        }
    }

    private void checkIfFileExists(String inputPath) throws OptionsValuesException {
        if (Files.notExists(Path.of(inputPath))) {
            throw OptionsValuesException.noSuchFile(inputPath);
        }
    }
}