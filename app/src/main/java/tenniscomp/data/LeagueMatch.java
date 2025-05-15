package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LeagueMatch extends Match {
    private final int leagueId;

    public LeagueMatch(final int matchId, final String type, final String winner, final String result,
                       final int courtId, final int refereeId, final int leagueId) {
        super(matchId, type, winner, result, courtId, refereeId);
        this.leagueId = leagueId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public final class DAO {
        
        public static List<LeagueMatch> getMatchesByLeague(final Connection connection, final int leagueId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_LEAGUE_MATCHES, leagueId);
                var resultSet = statement.executeQuery();
            ) {
                final var matches = new ArrayList<LeagueMatch>();
                while (resultSet.next()) {
                    matches.add(new LeagueMatch(
                        resultSet.getInt("id_partita"),
                        resultSet.getString("tipo"),
                        resultSet.getString("vincitore"),
                        resultSet.getString("risultato"),
                        resultSet.getInt("id_campo"),
                        resultSet.getInt("id_arbitro"),
                        resultSet.getInt("id_campionato")
                    ));
                }
                return matches;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}

