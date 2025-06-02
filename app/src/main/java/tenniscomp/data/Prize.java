package tenniscomp.data;

import java.sql.Connection;

public class Prize {
    private final int prizeId;
    private final int position;
    private final double value;
    private final int tournamentId;

    public Prize(final int prizeId, final int position, final double value, final int tournamentId) {
        this.prizeId = prizeId;
        this.position = position;
        this.value = value;
        this.tournamentId = tournamentId;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public int getPosition() {
        return position;
    }

    public double getValue() {
        return value;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public final class DAO {

        public static boolean insertPrize(final Connection connection, final int position, 
                final double value, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_PRIZE, position, value, tournamentId)
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
