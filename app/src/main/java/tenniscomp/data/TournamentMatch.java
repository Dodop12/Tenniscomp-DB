package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TournamentMatch extends Match {
    private final String date;
    private final int tournamentId;

    public TournamentMatch(final int matchId, final String date, final String result, 
            final int tournamentId, final int courtId, final Integer refereeId) {
        super(matchId, result, courtId, refereeId);
        this.date = date;
        this.tournamentId = tournamentId;
    }

    public String getDate() {
        return date;
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
                        resultSet.getString("data"),
                        resultSet.getString("risultato"),
                        resultSet.getInt("id_torneo"),
                        resultSet.getInt("id_campo"),
                        resultSet.getInt("id_arbitro")
                    ));
                }
                return matches;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}

