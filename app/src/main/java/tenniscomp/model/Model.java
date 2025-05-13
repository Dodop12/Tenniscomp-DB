package tenniscomp.model;

import java.util.List;

import tenniscomp.data.Card;
import tenniscomp.data.Club;
import tenniscomp.data.League;
import tenniscomp.data.Player;
import tenniscomp.data.Referee;
import tenniscomp.data.Tournament;

public interface Model {

    boolean registerPlayer(String surname, String name, String email, String birthDate,
            String gender, String phone, String username, String password);

    boolean loginPlayer(String username, String password);
    
    boolean loginReferee(String username, String password);

    Player getPlayerById(int playerId);

    Player getPlayerByUsername(String username);

    List<Player> getAllPlayers();

    Referee getRefereeByUsername(String username);

    boolean addClub(String name, String address, String city);

    Club getClubById(int clubId);

    List<Club> getAllClubs();

    boolean addCard(String cardNumber, String expiryDate);

    Card getCardById(int cardId);

    Card getCardByNumber(String cardNumber);

    boolean updatePlayerRanking(int playerId, String newRanking);

    boolean updatePlayerCard(int playerId, int cardId);

    boolean updatePlayerClub(final int playerId, final Integer clubId);

    boolean checkCardNumberExists(String cardNumber);

    boolean updateCardExpiryDate(int cardId, String newExpiryDate);

    boolean addTournament(String name, String startDate, String endDate, String registrationDeadline, 
            String type, String rankingLimit, double prizeMoney, int refereeId, int clubId);

    List<Tournament> getTournamentsByReferee(int refereeId);

    boolean addLeague(String series, String category, String gender, int year, int refereeId);

    List<League> getLeaguesByReferee(int refereeId);

}
