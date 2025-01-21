package test.softaria.statistics;

import java.util.Set;

public record Statistics(Set<String> missingPages, Set<String> newPages, Set<String> editedPages) {
}
