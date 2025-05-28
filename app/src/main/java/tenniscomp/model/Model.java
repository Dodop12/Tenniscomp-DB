package tenniscomp.model;

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
import tenniscomp.utils.Gender;
import tenniscomp.utils.LeagueCategory;
import tenniscomp.utils.LeagueSeries;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.Ranking;
import tenniscomp.utils.Surface;

public interface Model {

    boolean registerPlayer(String surname, String name, String email, String birthDate,
            Gender gender, String phone, String username, String password);
    boolean loginPlayer(String username, String password);
    Player getPlayerById(int playerId);
    Player getPlayerByUsername(String username);
    List<Player> getPlayersByCategoryAndGender(LeagueCategory category, Gender gender);
    List<Player> getAllPlayers();
    boolean updatePlayerRanking(int playerId, Ranking newRanking);
    boolean updatePlayerCard(int playerId, int cardId);
    boolean updatePlayerClub(final int playerId, final Integer clubId);

    boolean loginReferee(String username, String password);
    Referee getRefereeByUsername(String username);

    boolean addClub(String name, String address, String city);
    Club getClubById(int clubId);
    List<Club> getAllClubs();
    boolean addCourt(int number, Surface surface, boolean indoor, int clubId);
    Court getCourtById(int courtId);
    int getCourtCountByClub(int clubId);
    List<Court> getCourtsByClub(int clubId);
    boolean courtNumberExists(int number, int clubId);

    boolean addCard(String cardNumber, String expiryDate);
    Card getCardById(int cardId);
    Card getCardByNumber(String cardNumber);
    boolean checkCardNumberExists(String cardNumber);
    boolean updateCardExpiryDate(int cardId, String newExpiryDate);

    boolean addTournament(String name, String startDate, String endDate, String registrationDeadline, 
            MatchType type, Gender gender, Ranking rankingLimit, double prizeMoney, int refereeId, int clubId);
    Tournament getTournamentById(int tournamentId);
    List<TournamentRegistration> getTournamentRegistrations(int tournamentId);
    List<Player> getTournamentPlayers(int tournamentId);
    List<Tournament> getTournamentsByReferee(int refereeId);
    List<Tournament> getEligibleTournaments(Ranking playerRanking, Gender playerGender);
    boolean registerPlayerForTournament(int playerId, int tournamentId);
    boolean isPlayerRegisteredForTournament(int playerId, int tournamentId);
    boolean addTournamentMatch(String date, String result, int tournamentId, int courtId, 
            Integer refereeId, int winnerId, int opponentId); // For singles
    boolean addTournamentMatch(String date, String result, int tournamentId, int courtId, 
            Integer refereeId, List<Integer> winnerIds, List<Integer> opponentIds); // For doubles
    List<TournamentMatch> getTournamentMatches(int tournamentId);
    List<Player> getPlayersByTournamentMatch(int matchId);
    boolean isPlayerTournamentMatchWinner(int playerId, int matchId);

    boolean addLeague(LeagueSeries series, LeagueCategory category, Gender gender, int year, int refereeId);
    League getLeagueById(int leagueId);
    boolean registerTeamForLeague(int clubId, int leagueId, List<Integer> playerIds);
    boolean isPlayerInLeague(int playerId, int leagueId);
    List<League> getLeaguesByReferee(int refereeId);
    List<Player> getPlayersByTeam(int teamId);
    int getTeamPlayerCount(int teamId);
    boolean updatePlayerTeam(int playerId, Integer teamId);
    List<Team> getLeagueTeams(int leagueId);
    Club getClubByTeamId(int teamId);
    boolean addLeagueTie(String date, int leagueId, int homeTeamId, int awayTeamId);
    LeagueTie getLeagueTieById(int tieId);
    List<LeagueTie> getLeagueTies(int leagueId);
    boolean updateLeagueTieResult(int tieId, String result);
    boolean addLeagueMatch(String type, String result, int tieId, int courtId, 
            Integer refereeId, int winnerId, int opponentId); // For singles
    boolean addLeagueMatch(String type, String result, int tieId, int courtId, 
            Integer refereeId, List<Integer> winnerIds, List<Integer> opponentIds); // For doubles
    List<LeagueMatch> getLeagueTieMatches(int tieId);
    List<Player> getPlayersByLeagueMatch(int matchId);
    boolean isPlayerLeagueMatchWinner(int playerId, int matchId);
    int getTieMatchWinsByTeam(int teamId, int tieId);

}
