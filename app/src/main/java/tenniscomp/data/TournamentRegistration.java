package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TournamentRegistration {
    private final int registrationId;
    private final String date;
    private final int playerId;
    private final int tournamentId;

    public TournamentRegistration(final int registrationId, final String date,
            final int playerId, final int tournamentId) {
        this.registrationId = registrationId;
        this.date = date;
        this.playerId = playerId;
        this.tournamentId = tournamentId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public String getDate() {
        return date;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public final class DAO {

        public static List<TournamentRegistration> getRegistrationsByTournament(final Connection connection,
                final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENT_REGISTRATIONS, tournamentId);
                var resultSet = statement.executeQuery();
            ) {
                final var registrations = new ArrayList<TournamentRegistration>();
                while (resultSet.next()) {
                    registrations.add(new TournamentRegistration(
                            resultSet.getInt("registrationId"),
                            resultSet.getString("date"),
                            resultSet.getInt("playerId"),
                            resultSet.getInt("tournamentId")
                    ));
                }
                return registrations;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
