package tenniscomp.model;

import java.util.List;

import tenniscomp.data.Club;
import tenniscomp.data.Player;
import tenniscomp.data.Referee;

public interface Model {

    boolean registerPlayer(String surname, String name, String email, String birthDate,
            String gender, String phone, String username, String password);

    boolean loginPlayer(String username, String password);
    
    boolean loginReferee(String username, String password);

    List<Player> getAllPlayers();

    Player getPlayerByUsername(String username);

    Referee getRefereeByUsername(String username);

    List<Club> getAllClubs();

    boolean addClub(String name, String address, String city);

}
