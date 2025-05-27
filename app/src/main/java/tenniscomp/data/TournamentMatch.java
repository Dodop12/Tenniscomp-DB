package tenniscomp.data;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
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
                        resultSet.getInt("id_partita_torneo"),
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

        public static boolean insertTournamentMatch(final Connection connection, final String date, 
            final String result, final int tournamentId, final int courtId, final Integer refereeId,
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
                try (var matchStatement = connection.prepareStatement(Queries.ADD_TOURNAMENT_MATCH, 
                        Statement.RETURN_GENERATED_KEYS)) {
                    // Manually set parameters since the utility method cannot be used with generated keys
                    matchStatement.setString(1, date);
                    matchStatement.setString(2, result);
                    matchStatement.setInt(3, tournamentId);
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
                    try (var winnerStatement = DAOUtils.prepare(connection, Queries.ADD_TOURNAMENT_MATCH_PLAYER,
                            winnerId, matchId, true)) {
                        if (winnerStatement.executeUpdate() == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                }
                
                // Insert opponent(s) (losers)
                for (final int opponentId : opponentIds) {
                    try (var opponentStatement = DAOUtils.prepare(connection, Queries.ADD_TOURNAMENT_MATCH_PLAYER,
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

        public static boolean isPlayerWinner(final Connection connection, final int playerId, final int matchId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_PLAYER_WON_TOURNAMENT_MATCH,
                        playerId, matchId);
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
    }
}

