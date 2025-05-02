package tenniscomp.data;

public class Card {
    private final int cardId;
    private final String cardNumber;
    private final String expiryDate;
    private final int playerId;

    public Card(final int cardId, final String cardNumber, final String expiryDate, final int playerId) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.playerId = playerId;
    }

    public int getCardId() {
        return cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public int getPlayerId() {
        return playerId;
    }

    public final class DAO {

    }
}
