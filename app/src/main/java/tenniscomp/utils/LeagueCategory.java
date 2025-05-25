package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum LeagueCategory {
    OPEN, U10, U12, U14, U16, O45, O50, O55, O60;

    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(LeagueCategory::toString)
                .toList();
    }
}
