package test.softaria;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StatisticsCalculator {

    public Statistics calculate(HashMap<String, String> yesterdayPages, HashMap<String, String> todayPages) {

        Set<String> yesterdayURLs = yesterdayPages.keySet();
        Set<String> todayURLs = todayPages.keySet();

        Set<String> missingPages = yesterdayPages.keySet();
        Set<String> newPages = todayPages.keySet();

        missingPages.removeAll(todayURLs);
        newPages.removeAll(yesterdayURLs);

        todayURLs.removeAll(newPages);

        Set<String> editedPages = new HashSet<>(yesterdayURLs);
        for (String url : todayURLs) {
            if (!yesterdayPages.get(url).equals(todayPages.get(url))) {
                editedPages.add(url);
            }
        }

        return new Statistics(missingPages, newPages, editedPages);
    }
}
