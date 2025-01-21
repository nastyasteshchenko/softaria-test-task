package test.softaria.statistics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.common.io.Resources;
import test.softaria.PagesJsonReader;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

class StatisticsCalculatorTest {

    @Test
    void testCalculateStatistics() throws IOException {
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();

        Map<String, String> todayPages =
                PagesJsonReader.readToHashMap(Resources.getResource("today-pages.json").getPath());
        Map<String, String> yesterdayPages =
                PagesJsonReader.readToHashMap(Resources.getResource("yesterday-pages.json").getPath());

        Statistics expectedStatistics = getExpectedStatistics();
        Statistics actualStatistics = statisticsCalculator.calculate(yesterdayPages, todayPages);

        Assertions.assertEquals(expectedStatistics, actualStatistics);
    }

    private static Statistics getExpectedStatistics() {
        Set<String> expectedMissingPages = Set.of("https://example.com/image", "https://cherry.com/cherry");
        Set<String> expectedNewPages = Set.of("https://example.com/faq", "https://example.com/terms");
        Set<String> expectedEditedPages = Set.of("https://example.com/products", "https://example.com/careers",
                "https://example.com/services", "https://example.com/contact", "https://example.com/about");

        return new Statistics(expectedMissingPages, expectedNewPages, expectedEditedPages);
    }
}