package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TournamentMatch extends Match {
    private final int tournamentId;

    public TournamentMatch(final int matchId, final String type, final String winner, final String result,
                           final int courtId, final int refereeId, final int tournamentId) {
        super(matchId, type, winner, result, courtId, refereeId);
        this.tournamentId = tournamentId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public final class DAO {

        public static List<TournamentMatch> getMatchesByTournament(final Connection connection, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENT_MATCHES, tournamentId);
                var resultSet = statement.executeQuery();
            ) {
                final var matches = new ArrayList<TournamentMatch>();
                while (resultSet.next()) {
                    matches.add(new TournamentMatch(
                        resultSet.getInt("id_partita"),
                        resultSet.getString("tipo"),
                        resultSet.getString("vincitore"),
                        resultSet.getString("risultato"),
                        resultSet.getInt("id_campo"),
                        resultSet.getInt("id_arbitro"),
                        resultSet.getInt("id_torneo")
                    ));
                }
                return matches;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}

