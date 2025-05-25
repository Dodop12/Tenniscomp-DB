package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum LeagueSeries {
    A1, A2, B1, B2, C, D1, D2, D3, D4;

    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(LeagueSeries::toString)
                .toList();
    }
}
