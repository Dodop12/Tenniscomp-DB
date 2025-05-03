package tenniscomp.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import tenniscomp.data.Card;

public final class PlayerUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final List<String> RANKINGS = List.of(
            "1", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8",
            "3.1", "3.2", "3.3", "3.4", "3.5",
            "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.NC"
    );

    public static List<String> getAllRankings() {
        return RANKINGS;
    }

    public static String calculateCategory(final String birthDate) {
        // Category changes only on the first day of every year
        final int age = calculateAgeAtYearEnd(birthDate);

        // "Under" categories
        if (age <= 10)
            return "U10";
        if (age <= 12)
            return "U12";
        if (age <= 14)
            return "U14";
        if (age <= 16)
            return "U16";
        if (age <= 18)
            return "U18";
        if (age <= 30)
            return "U30";

        // "Over" categories
        final int baseCategory = Math.min((age / 5) * 5, 80); // Round down to the nearest multiple of 5, capping at 80
        return "O" + baseCategory;
    }

    public static boolean isCardExpired(final Card card) {
        if (card == null) {
            return true;
        }
        
        try {
            final var expiryDate = parseDate(card.getExpiryDate());
            return expiryDate.isBefore(LocalDate.now());
        } catch (final Exception e) {
            return true;
        }
    }

    private static int calculateAgeAtYearEnd(final String birthDate) {
        final var birth = parseDate(birthDate);
        
        final var yearEnd = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        return Period.between(birth, yearEnd).getYears();
    }

    private static LocalDate parseDate(final String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (final Exception e) {
            throw new IllegalArgumentException(
                dateString + " is not a valid date format. Expected format: yyyy-MM-dd",
                e
            );
        }
    }
}
