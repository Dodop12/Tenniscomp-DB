package tenniscomp.data;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tenniscomp.data.sql.DAOException;
import tenniscomp.data.sql.DAOUtils;
import tenniscomp.data.sql.Queries;
import tenniscomp.utils.Gender;
import tenniscomp.utils.LeagueCategory;
import tenniscomp.utils.Ranking;

public class Player {
    private final int playerId;
    private final String surname;
    private final String name;
    private final String email;
    private final String birthDate;
    private final Gender gender;
    private final String phone;
    private final String username;
    private final String password;
    private final Ranking ranking;
    private final Integer cardId;
    private final Integer clubId;
    private final Integer teamId;

    public Player(final int playerId, final String surname, final String name, final String email,
            final String birthDate, final Gender gender, final String phone, final String username,
            final String password, final Ranking ranking, final Integer cardId, final Integer clubId,
            final Integer teamId) {
        this.playerId = playerId;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.ranking = ranking;
        this.cardId = cardId;
        this.clubId = clubId;
        this.teamId = teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public Integer getCardId() {
        return cardId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String toString() {
        return surname + " " + name;
    }

    public final class DAO {

        public static boolean insertPlayer(final Connection connection, final String surname, final String name,
                final String email, final String birthDate, final Gender gender,
                final String phone, final String username, final String password) {
            final var convertedDate = getConvertedDateFormat(birthDate);
            try ( 
                var statement = DAOUtils.prepare(connection, Queries.ADD_PLAYER, surname, name, email,
                    convertedDate, gender.getCode(), phone, username, password);
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean checkLogin(final Connection connection, final String username, final String password) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_PLAYER_LOGIN, username, password);
                var resultSet = statement.executeQuery();
            ) {
                return resultSet.next();
            } catch (final Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static Player getPlayerById(final Connection connection, final int playerId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PLAYER_BY_ID, playerId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static Player getPlayerByUsername(final Connection connection, final String username) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PLAYER_BY_USERNAME, username);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Player> getPlayersByCategoryAndGender(final Connection connection, final LeagueCategory category,
                final Gender gender) {
            final var dateRange = category.getBirthDateRange();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PLAYERS_BY_GENDER_AND_BIRTH,
                        dateRange.x(), dateRange.y(), gender.getCode());
                var resultSet = statement.executeQuery();
            ) {
                final var players = new ArrayList<Player>();
                while (resultSet.next()) {
                    players.add(new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    ));
                }
                return players;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Player> getAllPlayers(final Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ALL_PLAYERS);
                var resultSet = statement.executeQuery();
            ) {
                final var players = new ArrayList<Player>();
                while (resultSet.next()) {
                    players.add(new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    ));
                }
                return players;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Player> getPlayersByTournament(final Connection connection, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENT_PLAYERS, tournamentId);
                var resultSet = statement.executeQuery();
            ) {
                final var players = new ArrayList<Player>();
                while (resultSet.next()) {
                    players.add(new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    ));
                }
                return players;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Player> getPlayersByTournamentMatch(final Connection connection, final int matchId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PLAYERS_BY_TOURNAMENT_MATCH, matchId);
                var resultSet = statement.executeQuery();
            ) {
                final var players = new ArrayList<Player>();
                while (resultSet.next()) {
                    players.add(new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    ));
                }
                return players;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Player> getPlayersByTeam(final Connection connection, final int teamId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PLAYERS_BY_TEAM, teamId);
                var resultSet = statement.executeQuery();
            ) {
                final var players = new ArrayList<Player>();
                while (resultSet.next()) {
                    players.add(new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    ));
                }
                return players;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Player> getPlayersByLeagueMatch(final Connection connection, final int matchId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PLAYERS_BY_LEAGUE_MATCH, matchId);
                var resultSet = statement.executeQuery();
            ) {
                final var players = new ArrayList<Player>();
                while (resultSet.next()) {
                    players.add(new Player(
                        resultSet.getInt("id_giocatore"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        Ranking.fromLabel(resultSet.getString("classifica")),
                        resultSet.getObject("id_tessera", Integer.class),
                        resultSet.getObject("id_circolo", Integer.class),
                        resultSet.getObject("id_squadra", Integer.class)
                    ));
                }
                return players;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean isPlayerInLeague(final Connection connection, final int playerId, final int leagueId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_PLAYER_IN_LEAGUE, playerId, leagueId);
                var resultSet = statement.executeQuery();
            ) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean updatePlayerRanking(final Connection connection, final int playerId, final Ranking newRanking) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_PLAYER_RANKING, newRanking.getLabel(), playerId);
            ) {
                return statement.executeUpdate() == 1;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean updatePlayerCard(final Connection connection, final int playerId, final int cardId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_PLAYER_CARD, cardId, playerId);
            ) {
                return statement.executeUpdate() == 1;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean updatePlayerClub(final Connection connection, final int playerId, final Integer clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_PLAYER_CLUB, clubId, playerId);
            ) {
                return statement.executeUpdate() == 1;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean updatePlayerTeam(final Connection connection, final int playerId, final Integer teamId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_PLAYER_TEAM, teamId, playerId);
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        private static String getConvertedDateFormat(final String date) {
            try {
                final var oldFormat = new SimpleDateFormat("dd/MM/yyyy");
                final var newFormat = new SimpleDateFormat("yyyy-MM-dd");
                final var parsedDate = oldFormat.parse(date);
                return newFormat.format(parsedDate);
            } catch (final ParseException e) {
                throw new DAOException("Errore nella conversione della data: " + e.getMessage(), e);
            }
        }

    }
    
}
