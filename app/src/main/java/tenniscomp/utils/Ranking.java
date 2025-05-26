package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum Ranking {
    RANK_1("1", 1),
    RANK_2_1("2.1", 21),
    RANK_2_2("2.2", 22),
    RANK_2_3("2.3", 23),
    RANK_2_4("2.4", 24),
    RANK_2_5("2.5", 25),
    RANK_2_6("2.6", 26),
    RANK_2_7("2.7", 27),
    RANK_2_8("2.8", 28),
    RANK_3_1("3.1", 31),
    RANK_3_2("3.2", 32),
    RANK_3_3("3.3", 33),
    RANK_3_4("3.4", 34),
    RANK_3_5("3.5", 35),
    RANK_4_1("4.1", 41),
    RANK_4_2("4.2", 42),
    RANK_4_3("4.3", 43),
    RANK_4_4("4.4", 44),
    RANK_4_5("4.5", 45),
    RANK_4_6("4.6", 46),
    RANK_4_NC("4.NC", 47);

    private final String label;
    private final int numericValue;

    Ranking(final String label, final int numericValue) {
        this.label = label;
        this.numericValue = numericValue;
    }

    public String getLabel() {
        return label;
    }

    public int getNumericValue() {
        return numericValue;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Ranking fromLabel(final String label) {
        return Arrays.stream(values())
                .filter(ranking -> ranking.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ranking found for label: " + label));
    }

    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(Ranking::getLabel)
                .toList();
    }
}
