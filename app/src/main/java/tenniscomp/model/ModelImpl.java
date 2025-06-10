package tenniscomp.model;

import java.sql.Connection;
import java.util.List;

import tenniscomp.data.Card;
import tenniscomp.data.Club;
import tenniscomp.data.Court;
import tenniscomp.data.League;
import tenniscomp.data.LeagueMatch;
import tenniscomp.data.LeagueTie;
import tenniscomp.data.Player;
import tenniscomp.data.Referee;
import tenniscomp.data.Team;
import tenniscomp.data.Tournament;
import tenniscomp.data.TournamentMatch;
import tenniscomp.data.TournamentRegistration;
import tenniscomp.data.Umpire;
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
    public boolean playerUsernameExists(final String username) {
        return Player.DAO.playerUsernameExists(this.connection, username);
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
    public int getPlayerSinglesMatchesCount(final int playerId) {
        return Player.DAO.getPlayerSinglesMatchesCount(this.connection, playerId);
    }

    @Override
    public int getPlayerSinglesWinsCount(final int playerId) {
        return Player.DAO.getPlayerSinglesWinsCount(this.connection, playerId);
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
    public boolean addUmpire(final String surname, final String name, final String email, final String birthDate,
            final Gender gender, final String phone, final String title) {
        return Umpire.DAO.insertUmpire(this.connection, surname, name, email, birthDate, gender, phone, title);
    }

    @Override
    public Umpire getUmpireById(final int umpireId) {
        return Umpire.DAO.getUmpireById(this.connection, umpireId);
    }

    @Override
    public List<Umpire> getAllUmpires() {
        return Umpire.DAO.getAllUmpires(this.connection);
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
    public boolean addCard(final String cardNumber) {
        return Card.DAO.insertCard(this.connection, cardNumber);
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
    public boolean renewCard(final int cardId) {
        return Card.DAO.renewCard(this.connection, cardId);
    }

    @Override
    public boolean addTournament(final String name, final String startDate, final String endDate, 
            final String registrationDeadline, final MatchType type, final Gender gender, final Ranking rankingLimit, 
            final double prizeMoney, List<Double> prizeDistribution, final int refereeId, final int clubId) {
        return Tournament.DAO.insertTournament(this.connection, name, startDate, endDate, 
            registrationDeadline, type, gender, rankingLimit, prizeMoney, prizeDistribution, refereeId, clubId);
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
    public List<TournamentMatch> getTournamentMatchesByPlayer(final int playerId) {
        return TournamentMatch.DAO.getMatchesByPlayer(this.connection, playerId);
    }

    @Override
    public List<Player> getPlayersByTournamentMatch(final int matchId) {
        return Player.DAO.getPlayersByTournamentMatch(this.connection, matchId);
    }

    @Override
    public boolean isPlayerTournamentMatchWinner(final int playerId, final int matchId) {
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
    public boolean registerTeamForLeague(final int clubId, final int leagueId, final List<Integer> playerIds) {
        return Team.DAO.registerTeamForLeague(this.connection, clubId, leagueId, playerIds);
    }

    @Override
    public boolean isPlayerInLeague(final int playerId, final int leagueId) {
        return Player.DAO.isPlayerInLeague(this.connection, playerId, leagueId);
    }

    @Override
    public List<League> getLeaguesByReferee(final int refereeId) {
        return League.DAO.getLeaguesByReferee(this.connection, refereeId);
    }

    @Override
    public List<Player> getPlayersByTeam(final int teamId) {
        return Player.DAO.getPlayersByTeam(this.connection, teamId);
    }

    @Override
    public int getTeamPlayerCount(final int teamId) {
        return Team.DAO.getTeamPlayerCount(this.connection, teamId);
    }

    @Override
    public boolean updatePlayerTeam(final int playerId, final Integer teamId) {
        return Player.DAO.updatePlayerTeam(this.connection, playerId, teamId);
    }

    @Override
    public List<Team> getLeagueTeams(final int leagueId) {
        return Team.DAO.getTeamsByLeague(this.connection, leagueId);
    }

    @Override
    public Club getClubByTeamId(final int teamId) {
        return Club.DAO.getClubByTeamId(this.connection, teamId);
    }

    @Override
    public boolean addLeagueTie(final String date, final int leagueId, final int homeTeamId, final int awayTeamId) {
        return LeagueTie.DAO.insertLeagueTie(this.connection, date, leagueId, homeTeamId, awayTeamId);
    }

    @Override
    public LeagueTie getLeagueTieById(final int tieId) {
        return LeagueTie.DAO.getLeagueTieById(this.connection, tieId);
    }

    @Override
    public List<LeagueTie> getLeagueTies(final int leagueId) {
        return LeagueTie.DAO.getTiesByLeague(this.connection, leagueId);
    }

    @Override
    public boolean updateLeagueTieResult(final int tieId, final String result) {
        return LeagueTie.DAO.updateLeagueTieResult(this.connection, tieId, result);
    }

    @Override
    public boolean addLeagueMatch(final MatchType type, final String result, final int tieId, 
            final int courtId, final Integer refereeId, final int winnerId, final int opponentId) {
        return addLeagueMatchGeneral(connection, type, result, tieId, courtId, refereeId,
                List.of(winnerId), List.of(opponentId));
    }

    @Override
    public boolean addLeagueMatch(final MatchType type, final String result, final int tieId, 
            final int courtId, final Integer refereeId, final List<Integer> winnerIds,
            final List<Integer> opponentIds) {
        return addLeagueMatchGeneral(connection, type, result, tieId, courtId, refereeId,
                winnerIds, opponentIds);
    }

    @Override
    public List<LeagueMatch> getLeagueTieMatches(final int tieId) {
        return LeagueMatch.DAO.getMatchesByLeagueTie(this.connection, tieId);
    }

    @Override
    public List<LeagueMatch> getLeagueMatchesByPlayer(final int playerId) {
        return LeagueMatch.DAO.getMatchesByPlayer(this.connection, playerId);
    }

    @Override
    public List<Player> getPlayersByLeagueMatch(final int matchId) {
        return Player.DAO.getPlayersByLeagueMatch(this.connection, matchId);
    }

    @Override
    public League getLeagueByMatchId(final int matchId) {
        return League.DAO.getLeagueByMatchId(this.connection, matchId);
    }

    @Override
    public boolean isPlayerLeagueMatchWinner(final int playerId, final int matchId) {
        return LeagueMatch.DAO.isPlayerWinner(this.connection, playerId, matchId);
    }

    @Override
    public int getTieMatchWinsByTeam(final int teamId, final int tieId) {
        return LeagueMatch.DAO.getTieMatchWinsByTeam(this.connection, teamId, tieId);
    }

    private boolean addTournamentMatchGeneral(final Connection connection, final String date, final String result,
            final int tournamentId, final int courtId, final Integer refereeId, final List<Integer> winnerIds,
            final List<Integer> opponentIds) {
        return TournamentMatch.DAO.insertTournamentMatch(connection, date, result, tournamentId, courtId,
                refereeId, winnerIds, opponentIds);
    }

    private boolean addLeagueMatchGeneral(final Connection connection, final MatchType type, final String result,
        final int tieId, final int courtId, final Integer refereeId, final List<Integer> winnerIds,
        final List<Integer> opponentIds) {
        return LeagueMatch.DAO.insertLeagueMatch(connection, type, result, tieId, courtId,
                refereeId, winnerIds, opponentIds);
    }
    
}
