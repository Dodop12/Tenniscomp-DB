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

        public static Card getCardByNumber(final Connection connection, final String cardNumber) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CARD_BY_NUMBER, cardNumber);
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

        public static boolean insertCard(final Connection connection, final String cardNumber, final String expiryDate) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_CARD, cardNumber, expiryDate);
            ) {
                return statement.executeUpdate() == 1;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean checkCardNumberExists(final Connection connection, final String cardNumber) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_CARD_NUMBER_EXISTS, cardNumber);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                }
                return false;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean updateCardExpiryDate(final Connection connection, final int cardId, final String newExpiryDate) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_CARD_EXPIRY_DATE, newExpiryDate, cardId);
            ) {
                return statement.executeUpdate() == 1;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

    }
}
