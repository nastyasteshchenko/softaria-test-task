package test.softaria;

import org.apache.commons.cli.ParseException;
import test.softaria.options.CommandLineParser;
import test.softaria.options.OptionType;
import test.softaria.options.OptionsValues;
import test.softaria.options.OptionsValuesException;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        OptionsValues optionsValues = getOptionsValues(args);
        if (optionsValues == null) {
            return;
        }

        HashMap<String, String> yesterdayPages = readPages(optionsValues.yesterdayPagesFile());
        if (yesterdayPages == null) {
            return;
        }
        HashMap<String, String> todayPages = readPages(optionsValues.todayPagesFile());
        if (todayPages == null) {
            return;
        }

        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        Statistics statistics = statisticsCalculator.calculate(yesterdayPages, todayPages);

        LetterPrinter letterPrinter = new LetterPrinter();
        letterPrinter.print(statistics, optionsValues.letterReceiverName());
    }

    private static OptionsValues getOptionsValues(String[] args) {
        try {
            CommandLineParser parser = new CommandLineParser();
            return parser.parse(args);
        } catch (ParseException e) {
            System.err.println(e.getMessage() + "\n\n" + OptionType.getAvailableOptionsInfo());
        } catch (OptionsValuesException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private static HashMap<String, String> readPages(String path) {
        try {
            return PagesJsonReader.readToHashMap(path);
        } catch (IOException e) {
            System.err.println("Error reading pages file: " + path);
        }
        return null;
    }
}