package tenniscomp.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public enum LeagueCategory {
    OPEN, U10, U12, U14, U16, O45, O50, O55, O60;

    public static List<String> getAllLabels() {
        return Arrays.stream(values())
                .map(LeagueCategory::toString)
                .toList();
    }

    public Pair<LocalDate, LocalDate> getBirthDateRange() {
        final var now = LocalDate.now();
        final int currentYear = now.getYear();
        final var minDate = LocalDate.of(1900, 1, 1);
        final int categoryAge = this == OPEN ? 0 : Integer.parseInt(this.name().substring(1));
        
        return switch (this) {
            case OPEN -> new Pair<>(minDate, now);
            
            case U10 -> new Pair<>(LocalDate.of(currentYear - 10, 1, 1), now);
            case U12 -> createUnderCategoryRange(currentYear, categoryAge);
            case U14 -> createUnderCategoryRange(currentYear, categoryAge);
            case U16 -> createUnderCategoryRange(currentYear, categoryAge);
            
            case O45 -> createOverCategoryRange(currentYear, categoryAge, 50);
            case O50 -> createOverCategoryRange(currentYear, categoryAge, 55);
            case O55 -> createOverCategoryRange(currentYear, categoryAge, 60);
            case O60 -> new Pair<>(minDate, LocalDate.of(currentYear - 60, 12, 31));
            default -> throw new IllegalArgumentException("Categoria non valida: " + this);
        };
    }
    
    private Pair<LocalDate, LocalDate> createUnderCategoryRange(final int currentYear, final int maxAge) {
        return new Pair<>(
            LocalDate.of(currentYear - maxAge, 1, 1),
            LocalDate.of(currentYear - (maxAge - 1), 12, 31)
        );
    }
    
    private Pair<LocalDate, LocalDate> createOverCategoryRange(final int currentYear, final int minAge, final int maxAge) {
        return new Pair<>(
            LocalDate.of(currentYear - maxAge, 1, 1),
            LocalDate.of(currentYear - minAge, 12, 31)
        );
    }
}
