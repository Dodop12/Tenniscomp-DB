package tenniscomp.model;

import tenniscomp.data.Player;
import tenniscomp.data.Referee;

public interface Model {

    boolean registerPlayer(final String name, final String surname, final String email, final String birthDate,
            final String gender, final String phone, final String username, final String password);

    boolean loginPlayer(final String username, final String password);
    
    boolean loginReferee(final String username, final String password);

    Player getPlayerByUsername(final String username);

    Referee getRefereeByUsername(final String username);

}
