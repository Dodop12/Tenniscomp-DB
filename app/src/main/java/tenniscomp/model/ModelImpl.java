package tenniscomp.model;

import java.sql.Connection;
import java.util.List;

import tenniscomp.data.Card;
import tenniscomp.data.Club;
import tenniscomp.data.Court;
import tenniscomp.data.League;
import tenniscomp.data.LeagueTie;
import tenniscomp.data.Player;
import tenniscomp.data.Referee;
import tenniscomp.data.Team;
import tenniscomp.data.Tournament;
import tenniscomp.data.TournamentMatch;
import tenniscomp.data.TournamentRegistration;
import tenniscomp.utils.Gender;
import tenniscomp.utils.LeagueCategory;
import tenniscomp.utils.LeagueSeries;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.Ranking;
import tenniscomp.utils.Surface;

public class ModelImpl implements Model {

    private final Connection connection;

    public ModelImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean registerPlayer(final String surname, final String name, final String email, final String birthDate,
            final Gender gender, final String phone, final String username, final String password) {
        return Player.DAO.insertPlayer(this.connection, surname, name, email,
            birthDate, gender, phone, username, password);
    }

    @Override
    public boolean loginPlayer(final String username, final String password) {
        return Player.DAO.checkLogin(this.connection, username, password);
    }

    @Override
    public Player getPlayerById(final int playerId) {
        return Player.DAO.getPlayerById(this.connection, playerId);
    }

    @Override
    public Player getPlayerByUsername(final String username) {
        return Player.DAO.getPlayerByUsername(this.connection, username);
    }

    @Override
    public List<Player> getPlayersByCategoryAndGender(final LeagueCategory category, final Gender gender) {
        return Player.DAO.getPlayersByCategoryAndGender(this.connection, category, gender);
    }

    @Override
    public List<Player> getAllPlayers() {
        return Player.DAO.getAllPlayers(this.connection);
    }

     @Override
    public boolean updatePlayerRanking(final int playerId, final Ranking newRanking) {
        return Player.DAO.updatePlayerRanking(this.connection, playerId, newRanking);
    }

    @Override
    public boolean updatePlayerCard(final int playerId, final int cardId) {
        return Player.DAO.updatePlayerCard(this.connection, playerId, cardId);
    }

    @Override
    public boolean updatePlayerClub(final int playerId, final Integer clubId) {
        return Player.DAO.updatePlayerClub(this.connection, playerId, clubId);
    }

    @Override
    public boolean loginReferee(final String username, final String password) {
        return Referee.DAO.checkLogin(this.connection, username, password);
    }

    @Override
    public Referee getRefereeByUsername(final String username) {
        return Referee.DAO.getRefereeByUsername(this.connection, username);
    }

    @Override
    public boolean addClub(final String name, final String address, final String city) {
        return Club.DAO.insertClub(this.connection, name, address, city);
    }

    @Override
    public Club getClubById(final int clubId) {
        return Club.DAO.getClubById(this.connection, clubId);
    }

    @Override
    public List<Club> getAllClubs() {
        return Club.DAO.getAllClubs(this.connection);
    }

    @Override
    public boolean addCourt(final int number, final Surface surface, final boolean indoor, final int clubId) {
        return Court.DAO.insertCourt(this.connection, number, surface, indoor, clubId);
    }

    @Override
    public Court getCourtById(final int courtId) {
        return Court.DAO.getCourtById(this.connection, courtId);
    }

    @Override
    public int getCourtCountByClub(final int clubId) {
        return Court.DAO.getCourtCountByClub(this.connection, clubId);
    }

    @Override
    public List<Court> getCourtsByClub(final int clubId) {
        return Court.DAO.getCourtsByClub(this.connection, clubId);
    }

    @Override
    public boolean courtNumberExists(final int number, final int clubId) {
        return Court.DAO.courtNumberExists(this.connection, number, clubId);
    }

    @Override
    public boolean addCard(final String cardNumber, final String expiryDate) {
        return Card.DAO.insertCard(this.connection, cardNumber, expiryDate);
    }

    @Override
    public Card getCardById(final int cardId) {
        return Card.DAO.getCardById(this.connection, cardId);
    }

    @Override
    public Card getCardByNumber(final String cardNumber) {
        return Card.DAO.getCardByNumber(this.connection, cardNumber);
    }

    @Override
    public boolean checkCardNumberExists(final String cardNumber) {
        return Card.DAO.checkCardNumberExists(this.connection, cardNumber);
    }

    @Override
    public boolean updateCardExpiryDate(final int cardId, final String newExpiryDate) {
        return Card.DAO.updateCardExpiryDate(this.connection, cardId, newExpiryDate);
    }

    @Override
    public boolean addTournament(final String name, final String startDate, final String endDate, 
            final String registrationDeadline, final MatchType type, final Gender gender, final Ranking rankingLimit, 
            final double prizeMoney, final int refereeId, final int clubId) {
        return Tournament.DAO.insertTournament(this.connection, name, startDate, endDate, 
            registrationDeadline, type, gender, rankingLimit, prizeMoney, refereeId, clubId);
    }

    @Override
    public Tournament getTournamentById(final int tournamentId) {
        return Tournament.DAO.getTournamentById(this.connection, tournamentId);
    }

    @Override
    public List<TournamentRegistration> getTournamentRegistrations(final int tournamentId) {
        return TournamentRegistration.DAO.getRegistrationsByTournament(this.connection, tournamentId);
    }

    @Override
    public List<Player> getTournamentPlayers(final int tournamentId) {
        return Player.DAO.getPlayersByTournament(this.connection, tournamentId);
    }

    @Override
    public List<Tournament> getTournamentsByReferee(final int refereeId) {
        return Tournament.DAO.getTournamentsByReferee(this.connection, refereeId);
    }

    @Override
    public List<Tournament> getEligibleTournaments(final Ranking playerRanking, final Gender playerGender) {
        return Tournament.DAO.getEligibleTournaments(this.connection, playerRanking, playerGender);
    }

    @Override
    public boolean registerPlayerForTournament(final int playerId, final int tournamentId) {
        return Tournament.DAO.registerPlayerForTournament(this.connection, playerId, tournamentId);
    }

    @Override
    public boolean isPlayerRegisteredForTournament(final int playerId, final int tournamentId) {
        return Tournament.DAO.isPlayerRegisteredForTournament(this.connection, playerId, tournamentId);
    }

    @Override
    public boolean addTournamentMatch(final String date, final String result, final int tournamentId, 
            final int courtId, final Integer refereeId, final int winnerId,
            final int opponentId) {
        return addTournamentMatchGeneral(connection, date, result, tournamentId, courtId, refereeId,
                List.of(winnerId), List.of(opponentId));
    }

    @Override
    public boolean addTournamentMatch(final String date, final String result, final int tournamentId, 
            final int courtId, final Integer refereeId, final List<Integer> winnerIds,
            final List<Integer> opponentIds) {
        return addTournamentMatchGeneral(connection, date, result, tournamentId, courtId, refereeId,
                winnerIds, opponentIds);
    }

    @Override
    public List<TournamentMatch> getTournamentMatches(final int tournamentId) {
        return TournamentMatch.DAO.getMatchesByTournament(this.connection, tournamentId);
    }

    @Override
    public List<Player> getPlayersByTournamentMatch(final int matchId) {
        return Player.DAO.getPlayersByTournamentMatch(this.connection, matchId);
    }

    @Override
    public boolean isPlayerWinner(final int playerId, final int matchId) {
        return TournamentMatch.DAO.isPlayerWinner(this.connection, playerId, matchId);
    }

    @Override
    public boolean addLeague(final LeagueSeries series, final LeagueCategory category, final Gender gender, 
            final int year, final int refereeId) {
        return League.DAO.insertLeague(this.connection, series, category, gender, year, refereeId);
    }

    @Override
    public League getLeagueById(final int leagueId) {
        return League.DAO.getLeagueById(this.connection, leagueId);
    }

    @Override
    public boolean registerTeamForLeague(final int clubId, final int leagueId, List<Integer> playerIds) {
        return Team.DAO.registerTeamForLeague(this.connection, clubId, leagueId, playerIds);
    }

    @Override
    public boolean isPlayerInLeague(final int playerId, final int leagueId) {
        return Player.DAO.isPlayerInLeague(this.connection, playerId, leagueId);
    }

    @Override
    public boolean updatePlayerTeam(final int playerId, final int teamId) {
        return Player.DAO.updatePlayerTeam(this.connection, playerId, teamId);
    }

    @Override
    public List<Team> getLeagueTeams(final int leagueId) {
        return Team.DAO.getTeamsByLeague(this.connection, leagueId);
    }

    @Override
    public boolean addLeagueTie(String date, int leagueId, int homeTeamId, int awayTeamId) {
        return LeagueTie.DAO.insertLeagueTie(this.connection, date, leagueId, homeTeamId, awayTeamId);
    }

    @Override
    public List<LeagueTie> getLeagueTies(final int leagueId) {
        return LeagueTie.DAO.getTiesByLeague(this.connection, leagueId);
    }

    @Override
    public List<League> getLeaguesByReferee(final int refereeId) {
        return League.DAO.getLeaguesByReferee(this.connection, refereeId);
    }

    @Override
    public Club getClubByTeamId(final int teamId) {
        return Club.DAO.getClubByTeamId(this.connection, teamId);
    }

    private boolean addTournamentMatchGeneral(final Connection connection, final String date, final String result,
            final int tournamentId, final int courtId, final Integer refereeId, final List<Integer> winnerIds,
            final List<Integer> opponentIds) {
        return TournamentMatch.DAO.insertTournamentMatch(connection, date, result, tournamentId, courtId,
                refereeId, winnerIds, opponentIds);
    }
    
}
