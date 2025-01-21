package test.softaria;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StatisticsCalculator {

    public Statistics calculate(Map<String, String> yesterdayPages, Map<String, String> todayPages) {

        Set<String> yesterdayURLs = Set.copyOf(yesterdayPages.keySet());
        Set<String> todayURLs = Set.copyOf(todayPages.keySet());

        Set<String> missingPages = yesterdayURLs.stream()
                .filter(url -> !todayURLs.contains(url))
                .collect(Collectors.toSet());
        Set<String> newPages = todayURLs.stream()
                .filter(url -> !yesterdayURLs.contains(url))
                .collect(Collectors.toSet());

        Set<String> maybeEditedPagesURLs = todayURLs.stream()
                .filter(url -> !newPages.contains(url))
                .collect(Collectors.toSet());

        Set<String> editedPages = new HashSet<>();
        maybeEditedPagesURLs.stream()
                .filter(url -> !yesterdayPages.get(url).equals(todayPages.get(url)))
                .forEach(editedPages::add);

        return new Statistics(missingPages, newPages, editedPages);
    }
}
