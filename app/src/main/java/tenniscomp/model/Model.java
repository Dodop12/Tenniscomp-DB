package tenniscomp.model;

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

public interface Model {

    boolean registerPlayer(String surname, String name, String email, String birthDate,
            String gender, String phone, String username, String password);
    boolean loginPlayer(String username, String password);
    Player getPlayerById(int playerId);
    Player getPlayerByUsername(String username);
    List<Player> getAllPlayers();
    boolean updatePlayerRanking(int playerId, String newRanking);
    boolean updatePlayerCard(int playerId, int cardId);
    boolean updatePlayerClub(final int playerId, final Integer clubId);

    boolean loginReferee(String username, String password);
    Referee getRefereeByUsername(String username);

    boolean addClub(String name, String address, String city);
    Club getClubById(int clubId);
    List<Club> getAllClubs();
    boolean addCourt(int number, String surface, boolean indoor, int clubId);
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
            String type, String gender, String rankingLimit, double prizeMoney, int refereeId, int clubId);
    Tournament getTournamentById(int tournamentId);
    List<TournamentRegistration> getTournamentRegistrations(int tournamentId);
    List<Player> getTournamentPlayers(int tournamentId);
    List<TournamentMatch> getTournamentMatches(int tournamentId);
    List<Tournament> getTournamentsByReferee(int refereeId);
    List<Tournament> getEligibleTournaments(String playerRanking, String playerGender);
    boolean registerPlayerForTournament(int playerId, int tournamentId);
    boolean isPlayerRegisteredForTournament(int playerId, int tournamentId);

    boolean addLeague(String series, String category, String gender, int year, int refereeId);
    League getLeagueById(int leagueId);
    List<Team> getLeagueTeams(int leagueId);
    List<LeagueTie> getLeagueTies(int leagueId);
    List<League> getLeaguesByReferee(int refereeId);
    Club getClubByTeamId(int teamId);

}
