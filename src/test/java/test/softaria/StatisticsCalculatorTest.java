package test.softaria;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

class StatisticsCalculatorTest {

    @Test
    void calculateStatisticsTest() throws IOException {
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        PagesJsonReader pagesJsonReader = new PagesJsonReader();

        Map<String, String> todayPages = pagesJsonReader.readToHashMap(getResource("today-pages.json"));
        Map<String, String> yesterdayPages = pagesJsonReader.readToHashMap(getResource("yesterday-pages.json"));

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

    public String getResource(String path) throws IOException {
        return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
    }
}