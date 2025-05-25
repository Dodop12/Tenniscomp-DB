package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum Surface {
    TERRA("Terra rossa"),
    ERBA("Erba"),
    CEMENTO("Cemento"),
    SINTETICO("Sintetico");

    private final String label;

    Surface(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(Surface::getLabel)
                .toList();
    }
}
