package tenniscomp.model;

import java.sql.Connection;
import java.util.List;

import tenniscomp.data.Card;
import tenniscomp.data.Club;
import tenniscomp.data.League;
import tenniscomp.data.LeagueMatch;
import tenniscomp.data.Player;
import tenniscomp.data.Referee;
import tenniscomp.data.Team;
import tenniscomp.data.Tournament;
import tenniscomp.data.TournamentMatch;
import tenniscomp.data.TournamentRegistration;

public class ModelImpl implements Model {

    private final Connection connection;

    public ModelImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean registerPlayer(final String surname, final String name, final String email, final String birthDate,
            final String gender, final String phone, final String username, final String password) {
        return Player.DAO.insertPlayer(this.connection, surname, name, email,
            birthDate, gender, phone, username, password);
    }

    @Override
    public boolean loginPlayer(final String username, final String password) {
        return Player.DAO.checkLogin(this.connection, username, password);
    }

    @Override
    public boolean loginReferee(final String username, final String password) {
        return Referee.DAO.checkLogin(this.connection, username, password);
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
    public List<Player> getAllPlayers() {
        return Player.DAO.getAllPlayers(this.connection);
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
    public boolean updatePlayerRanking(final int playerId, final String newRanking) {
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
    public boolean checkCardNumberExists(final String cardNumber) {
        return Card.DAO.checkCardNumberExists(this.connection, cardNumber);
    }

    @Override
    public boolean updateCardExpiryDate(final int cardId, final String newExpiryDate) {
        return Card.DAO.updateCardExpiryDate(this.connection, cardId, newExpiryDate);
    }

    @Override
    public boolean addTournament(final String name, final String startDate, final String endDate, 
            final String registrationDeadline, final String type, final String rankingLimit, 
            final double prizeMoney, final int refereeId, final int clubId) {
        return Tournament.DAO.insertTournament(this.connection, name, startDate, endDate, 
            registrationDeadline, type, rankingLimit, prizeMoney, refereeId, clubId);
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
    public List<TournamentMatch> getTournamentMatches(final int tournamentId) {
        return TournamentMatch.DAO.getMatchesByTournament(this.connection, tournamentId);
    }

    @Override
    public List<Tournament> getTournamentsByReferee(final int refereeId) {
        return Tournament.DAO.getTournamentsByReferee(this.connection, refereeId);
    }

    @Override
    public boolean addLeague(final String series, final String category, final String gender, 
            final int year, final int refereeId) {
        return League.DAO.insertLeague(this.connection, series, category, gender, year, refereeId);
    }

    @Override
    public League getLeagueById(final int leagueId) {
        return League.DAO.getLeagueById(this.connection, leagueId);
    }

    @Override
    public List<Team> getLeagueTeams(final int leagueId) {
        return null; // TODO
    }

    @Override
    public List<LeagueMatch> getLeagueMatches(final int leagueId) {
        return LeagueMatch.DAO.getMatchesByLeague(this.connection, leagueId);
    }

    @Override
    public List<League> getLeaguesByReferee(final int refereeId) {
        return League.DAO.getLeaguesByReferee(this.connection, refereeId);
    }
    
}
