package tenniscomp.data;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class LeagueMatch extends Match {
    private final String type;
    private final int tieId;

    public LeagueMatch(final int matchId, final String type, final String result,
            final int tieId, final int courtId, final int refereeId) {
        super(matchId, result, courtId, refereeId);
        this.type = type;
        this.tieId = tieId;
    }

    public String getType() {
        return type;
    }

    public int getTieId() {
        return tieId;
    }

    public final class DAO {

        public static boolean insertLeagueMatch(final Connection connection, final String type, 
                final String result, final int tieId, final int courtId, final Integer refereeId,
                final List<Integer> winnerIds, final List<Integer> opponentIds) {
            try {
                connection.setAutoCommit(false); // Allow transaction rollback in case of failures

                // Check player parameters validity
                if (winnerIds == null || opponentIds == null ||
                        (winnerIds.size() != 1 && winnerIds.size() != 2) ||
                        (opponentIds.size() != 1 && opponentIds.size() != 2)) {
                    throw new IllegalArgumentException("Invalid number of players (must be 1 or 2 per team).");
                }
                
                // Insert the match
                int matchId;
                try (var matchStatement = connection.prepareStatement(Queries.ADD_LEAGUE_MATCH, 
                        Statement.RETURN_GENERATED_KEYS)) {
                    // Manually set parameters since the utility method cannot be used with generated keys
                    matchStatement.setString(1, type);
                    matchStatement.setString(2, result);
                    matchStatement.setInt(3, tieId);
                    matchStatement.setInt(4, courtId);
                    if (refereeId != null) {
                        matchStatement.setInt(5, refereeId);
                    } else {
                        matchStatement.setNull(5, Types.INTEGER);
                    }
                    if (matchStatement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }
                    
                    // Get the generated match ID
                    try (var generatedKeys = matchStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            matchId = generatedKeys.getInt(1);
                        } else {
                            connection.rollback();
                            return false;
                        }
                    }
                }
                
                // Insert winner(s)
                for (final int winnerId : winnerIds) {
                    try (var winnerStatement = DAOUtils.prepare(connection, Queries.ADD_LEAGUE_MATCH_PLAYER,
                            winnerId, matchId, true)) {
                        if (winnerStatement.executeUpdate() == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                }
                
                // Insert opponent(s) (losers)
                for (final int opponentId : opponentIds) {
                    try (var opponentStatement = DAOUtils.prepare(connection, Queries.ADD_LEAGUE_MATCH_PLAYER,
                            opponentId, matchId, false)) {
                        if (opponentStatement.executeUpdate() == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                }
                
                connection.commit();
                return true;
            } catch (final Exception e) {
                try {
                    connection.rollback();
                } catch (final Exception rollbackEx) {
                    throw new DAOException(rollbackEx);
                }
                throw new DAOException(e);
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (final Exception autoCommitEx) {
                    throw new DAOException(autoCommitEx);
                }
            }
        }
        
        public static List<LeagueMatch> getMatchesByLeagueTie(final Connection connection, final int tieId) {
            try (
                final var statement = DAOUtils.prepare(connection, Queries.GET_MATCHES_BY_TIE_ID, tieId);
                final var resultSet = statement.executeQuery()
            ) {
                final List<LeagueMatch> matches = new ArrayList<>();
                while (resultSet.next()) {
                    matches.add(new LeagueMatch(
                        resultSet.getInt("id_partita_campionato"),
                        resultSet.getString("tipo"),
                        resultSet.getString("risultato"),
                        tieId,
                        resultSet.getInt("id_campo"),
                        resultSet.getInt("id_arbitro")
                    ));
                }
                return matches;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static int getTieMatchWinsByTeam(final Connection connection, final int teamId, final int tieId) {
            try (
                final var statement = DAOUtils.prepare(connection, Queries.GET_TIE_MATCH_WINS_BY_TEAM, teamId, tieId);
                final var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean isPlayerWinner(final Connection connection, final int playerId, final int matchId) {
            try (
                final var statement = DAOUtils.prepare(connection, Queries.CHECK_PLAYER_WON_LEAGUE_MATCH,
                        playerId, matchId);
                final var resultSet = statement.executeQuery()
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                }
                return false;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}

