package tenniscomp.model;

import java.sql.Connection;
import java.util.List;

import tenniscomp.data.Club;
import tenniscomp.data.Player;
import tenniscomp.data.Referee;

public class ModelImpl implements Model {

    private final Connection connection;

    public ModelImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean registerPlayer(final String surname, final String name, final String email, final String birthDate,
            final String gender, final String phone, final String username, final String password) {
        return Player.DAO.insertPlayer(this.connection, surname, name, email,
            birthDate, gender, phone, username, password) == 1;
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
    public List<Player> getAllPlayers() {
        return Player.DAO.getAllPlayers(this.connection);
    }

    @Override
    public Player getPlayerByUsername(final String username) {
        return Player.DAO.getPlayerByUsername(this.connection, username);
    }

    @Override
    public Referee getRefereeByUsername(final String username) {
        return Referee.DAO.getRefereeByUsername(this.connection, username);
    }

    @Override
    public List<Club> getAllClubs() {
        return Club.DAO.getAllClubs(this.connection);
    }
    
    @Override
    public boolean addClub(final String name, final String address, final String city) {
        return Club.DAO.insertClub(this.connection, name, address, city) == 1;
    }

    
}
