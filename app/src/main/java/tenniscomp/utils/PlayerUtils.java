package tenniscomp.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import tenniscomp.data.Card;

public final class PlayerUtils {

    private static final DateTimeFormatter YMD_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DMY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final List<String> RANKINGS = List.of(
            "1", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8",
            "3.1", "3.2", "3.3", "3.4", "3.5",
            "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.NC"
    );

    private static final List<String> LEAGUE_SERIES = List.of(
            "A1", "A2", "B1", "B2", "C", "D1", "D2", "D3", "D4"
    );

    private static final List<String> LEAGUE_CATEGORIES = List.of(
            "Open", "U10", "U12", "U14", "U16", "U18", "O45", "O50", "O55", "O60"
    );

    private static final List<String> MATCH_TYPES = List.of(
            "Singolare", "Doppio"
    );

    private PlayerUtils() {
        
    }

    public static List<String> getAllRankings() {
        return RANKINGS;
    }

    public static List<String> getLeagueSeries() {
        return LEAGUE_SERIES;
    }

    public static List<String> getLeagueCategories() {
        return LEAGUE_CATEGORIES;
    }

    public static List<String> getMatchTypes() {
        return MATCH_TYPES;
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

    public static String generateCardNumber() {
        final var random = new Random();
        // Generate a random 7-digit number between 1000000 and 9999999
        return String.format("%07d", 1000000 + random.nextInt(9000000));
    }
    
    public static String generateCardExpiryDate() {
        // When the card is generated, it is valid until the end of the current year
        final int currentYear = LocalDate.now().getYear();
        return LocalDate.of(currentYear, 12, 31).format(YMD_DATE_FORMATTER);
    }

    /**
     * Converts a date from the format yyyy-MM-dd to dd/MM/yyyy.
     * @param date
     * @return
     */
    public static String convertDateFormat(final String date) {
        try {
            final var parsedDate = LocalDate.parse(date, YMD_DATE_FORMATTER);
            return parsedDate.format(DMY_DATE_FORMATTER);
        } catch (final Exception e) {
            throw new IllegalArgumentException(
                date + " is not a valid date format. Expected format: yyyy-MM-dd",
                e
            );
        }
    }

    private static int calculateAgeAtYearEnd(final String birthDate) {
        final var birth = parseDate(birthDate);
        
        final var yearEnd = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        return Period.between(birth, yearEnd).getYears();
    }

    private static LocalDate parseDate(final String dateString) {
        try {
            return LocalDate.parse(dateString, YMD_DATE_FORMATTER);
        } catch (final Exception e) {
            throw new IllegalArgumentException(
                dateString + " is not a valid date format. Expected format: yyyy-MM-dd",
                e
            );
        }
    }
}
