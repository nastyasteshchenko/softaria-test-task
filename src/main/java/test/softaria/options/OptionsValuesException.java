package test.softaria.options;

public class OptionsValuesException extends Exception {
    private OptionsValuesException(String message) {
        super(message);
    }

    public static OptionsValuesException duplicateOption(String option) {
        return new OptionsValuesException(String.format("Duplicated option '%s'.", option));
    }

    public static OptionsValuesException notReadableFile(String filePath) {
        return new OptionsValuesException(String.format("Cannot read from '%s'.", filePath));
    }

    public static OptionsValuesException noSuchFile(String filePath) {
        return new OptionsValuesException(String.format("Cannot access '%s': No such file or directory.", filePath));
    }
}