package tenniscomp.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class PlayerUtils {

    public static String calculateCategory(final String birthDate) {
        final int age = calculateAge(birthDate);

        // "Under" categories
        if (age <= 10) return "U10";
        if (age <= 12) return "U12";
        if (age <= 14) return "U14";
        if (age <= 16) return "U16";
        if (age <= 18) return "U18";
        if (age <= 30) return "U30";

        // "Over" categories
        final int baseCategory = Math.min((age / 5) * 5, 80); // Round down to the nearest multiple of 5, capping at 80
        return "O" + baseCategory;
    }

    private static int calculateAge(final String birthDate) {
        try {
            final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final var birth = LocalDate.parse(birthDate, formatter);
            
            final var now = LocalDate.now();
            return Period.between(birth, now).getYears();
        } catch (final Exception e) {
            throw new IllegalArgumentException(birthDate + " is not a valid date format. Expected format: yyyy-MM-dd", e);
        }
    }
}
