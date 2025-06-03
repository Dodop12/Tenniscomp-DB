package tenniscomp.utils;

import java.util.Arrays;
import java.util.List;

public enum Gender {
    MALE("M", "Maschio", "Maschile"),
    FEMALE("F", "Femmina", "Femminile");

    private final String code;
    private final String label;
    private final String type;

    Gender(final String code, final String label, final String type) {
        this.code = code;
        this.label = label;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
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

    public static List<String> getAllTypes() {
        return Arrays.stream(values())
                .map(Gender::getType)
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

    public static Gender fromType(final String type) {
        return Arrays.stream(values())
                .filter(gender -> gender.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No gender found for label: " + type));
    }
}
