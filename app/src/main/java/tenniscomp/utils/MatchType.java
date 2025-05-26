package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum MatchType {
    SINGOLARE("Singolare"),
    DOPPIO("Doppio");

    private final String label;

    MatchType(final String label) {
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
                .map(MatchType::getLabel)
                .toList();
    }

    public static MatchType fromLabel(final String label) {
        return Arrays.stream(values())
                .filter(type -> type.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No match type found for label: " + label));
    }
}
