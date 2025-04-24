package tenniscomp.model;

import java.sql.Connection;

import tenniscomp.data.Player;
import tenniscomp.data.Referee;

public class ModelImpl implements Model {

    private final Connection connection;

    public ModelImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean registerPlayer(final String name, final String surname, final String email, final String birthDate,
            final String gender, final String phone, final String username, final String password) {
        return Player.DAO.insertPlayer(this.connection, name, surname, email,
            birthDate, gender, phone, username, password) == 1;
    }

    @Override
    public boolean loginPlayer(String username, String password) {
        return Player.DAO.checkLogin(this.connection, username, password);
    }

    @Override
    public boolean loginReferee(String username, String password) {
        return Referee.DAO.checkLogin(this.connection, username, password);
    }
    
}
