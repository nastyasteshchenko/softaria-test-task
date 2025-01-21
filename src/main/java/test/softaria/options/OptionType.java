package test.softaria.options;

public enum OptionType {

    YESTERDAY_PAGES_FILE("y", "set yesterday pages file",
            "path", 1),
    TODAY_PAGES_FILE("t", "set today pages file",
            "path", 1),
    RECEIVER_NAME("n", "set receiver name (first name and patronymic)",
            "first name and patronymic", 2);

    private final String shortName;
    private final String description;
    private final String argName;
    private final int argsNumber;

    OptionType(String shortName, String description, String argName, int argsNumber) {
        this.shortName = shortName;
        this.description = description;
        this.argName = argName;
        this.argsNumber = argsNumber;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getArgName() {
        return argName;
    }

    public int getArgsNumber() {
        return argsNumber;
    }

    public static String getAvailableOptionsInfo(){
        StringBuilder sb = new StringBuilder();
        for (OptionType optionType : OptionType.values()) {
            sb.append("-")
                    .append(optionType.getShortName())
                    .append(" <")
                    .append(optionType.argName)
                    .append(">")
                    .append("\t - ")
                    .append(optionType.getDescription())
                    .append("\n");
        }
        return sb.toString();
    }
}
