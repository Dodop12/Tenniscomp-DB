package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private final int tournamentId;
    private final String name;
    private final String startDate;
    private final String endDate;
    private final String registrationDeadline;
    private final String type;
    private final String gender;
    private final String rankingLimit;
    private final double prizeMoney;
    private final int refereeId;
    private final int clubId;

    public Tournament(final int competitionId, final String name, final String startDate, final String endDate,
            final String registrationDeadline, final String type, final String gender, final String rankingLimit,
            final double prizeMoney, final int refereeId, final int clubId) {
        this.tournamentId = competitionId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;
        this.type = type;
        this.gender = gender;
        this.rankingLimit = rankingLimit;
        this.prizeMoney = prizeMoney;
        this.refereeId = refereeId;
        this.clubId = clubId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getRankingLimit() {
        return rankingLimit;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public int getRefereeId() {
        return refereeId;
    }

    public int getClubId() {
        return clubId;
    }

    public final class DAO {

        public static boolean insertTournament(final Connection connection, final String name, final String startDate, 
                final String endDate, final String registrationDeadline, final String type, final String gender,
                final String rankingLimit, final double prizeMoney, final int refereeId, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_TOURNAMENT, name, startDate, endDate, 
                        registrationDeadline, type, gender, rankingLimit, prizeMoney, refereeId, clubId)
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static Tournament getTournamentById(final Connection connection, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENT_BY_ID, tournamentId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Tournament(
                            resultSet.getInt("id_torneo"),
                            resultSet.getString("nome"),
                            resultSet.getString("data_inizio"),
                            resultSet.getString("data_fine"),
                            resultSet.getString("scadenza_iscrizioni"),
                            resultSet.getString("tipo"),
                            resultSet.getString("sesso"),
                            resultSet.getString("limite_classifica"),
                            resultSet.getDouble("montepremi"),
                            resultSet.getInt("id_ga"),
                            resultSet.getInt("id_circolo")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Tournament> getTournamentsByReferee(final Connection connection, final int refereeId) {           
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENTS_BY_REFEREE, refereeId);
                var resultSet = statement.executeQuery();
                ) {
                    final var tournaments = new ArrayList<Tournament>();
                    while (resultSet.next()) {
                        tournaments.add(new Tournament(
                                resultSet.getInt("id_torneo"),
                                resultSet.getString("nome"),
                                resultSet.getString("data_inizio"),
                                resultSet.getString("data_fine"),
                                resultSet.getString("scadenza_iscrizioni"),
                                resultSet.getString("tipo"),
                                resultSet.getString("sesso"),
                                resultSet.getString("limite_classifica"),
                                resultSet.getDouble("montepremi"),
                                resultSet.getInt("id_ga"),
                                resultSet.getInt("id_circolo")
                        ));
                    }
                    return tournaments;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
        
    }
}
