package test.softaria;

import test.softaria.statistics.Statistics;

public class LetterPrinter {

    private static final String DELIMITER = ", ";

    private static final String LETTER_TEMPLATE = """
            Здравствуйте, дорогая %s.
            
            За последние сутки во вверенных Вам сайтах произошли следующие изменения:
            
            Исчезли следующие страницы: %s
            Появились следующие новые страницы: %s
            Изменились следующие страницы: %s
            
            С уважением,
            автоматизированная система
            мониторинга.
            """;

    public void print(Statistics statistics, String receiverName) {
        String missingPages = String.join(DELIMITER, statistics.missingPages());
        String newPages = String.join(DELIMITER, statistics.newPages());
        String editedPages = String.join(DELIMITER, statistics.editedPages());

        String letter = String.format(LETTER_TEMPLATE, receiverName, missingPages, newPages, editedPages);
        System.out.println(letter);
    }
}
