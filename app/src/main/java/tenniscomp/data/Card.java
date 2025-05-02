package tenniscomp.data;

import java.sql.Connection;

public class Card {
    private final int cardId;
    private final String cardNumber;
    private final String expiryDate;

    public Card(final int cardId, final String cardNumber, final String expiryDate) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
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

    public final class DAO {
        
        public static Card getCardById(final Connection connection, final int cardId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CARD_BY_ID, cardId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Card(
                        resultSet.getInt("id_tessera"),
                        resultSet.getString("numero"),
                        resultSet.getString("scadenza")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
