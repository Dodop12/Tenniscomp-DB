package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LeagueTie {
    private final int tieId;
    private final String date;
    private final String result;
    private final int leagueId;
    private final int homeTeamId;
    private final int awayTeamId;

    public LeagueTie(final int matchupId, final String date, final String result,
            final int leagueId, final int homeTeamId, final int awayTeamId) {
        this.tieId = matchupId;
        this.date = date;
        this.result = result;
        this.leagueId = leagueId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
    }

    public int getTieId() {
        return tieId;
    }

    public String getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public final class DAO {

        public static boolean insertLeagueTie(final Connection connection, final String date,
                final int leagueId, final int homeTeamId, final int awayTeamId) {
            try (
                final var statement = DAOUtils.prepare(connection, Queries.ADD_LEAGUE_TIE, 
                        date, leagueId, homeTeamId, awayTeamId)
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<LeagueTie> getTiesByLeague(final Connection connection, final int leagueId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_LEAGUE_TIES, leagueId);
                var resultSet = statement.executeQuery();
            ) {
                final var ties = new ArrayList<LeagueTie>();
                while (resultSet.next()) {
                    ties.add(new LeagueTie(
                        resultSet.getInt("id_incontro"),
                        resultSet.getString("data"),
                        resultSet.getString("risultato"),
                        resultSet.getInt("id_campionato"),
                        resultSet.getInt("id_squadra_casa"),
                        resultSet.getInt("id_squadra_ospite")
                    ));
                }
                return ties;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
