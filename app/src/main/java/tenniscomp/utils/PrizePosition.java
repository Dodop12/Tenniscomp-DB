package tenniscomp.utils;

public enum PrizePosition {
    WINNER(1, "Vincitore"),
    FINALIST(1, "Finalista"),
    SEMIFINALIST(2, "Semifinalista"),
    QUARTERFINALIST(4, "Quarti di finale");

    private final int multiplier;
    private final String label;

    PrizePosition(final int multiplier, final String label) {
        this.multiplier = multiplier;
        this.label = label;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public String getLabel() {
        return label;
    }
}

