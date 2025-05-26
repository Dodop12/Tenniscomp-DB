package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum Gender {
    MALE("M", "Maschile"),
    FEMALE("F", "Femminile");

    private final String code;
    private final String label;

    Gender(final String code, final String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static List<String> getAllCodes() {
        return Arrays.stream(values())
                .map(Gender::getCode)
                .toList();
    }

    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(Gender::getLabel)
                .toList();
    }

    public static Gender fromCode(final String code) {
        return Arrays.stream(values())
                .filter(gender -> gender.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No gender found for code: " + code));
    }

    public static Gender fromLabel(final String label) {
        return Arrays.stream(values())
                .filter(gender -> gender.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No gender found for label: " + label));
    }
}
