package tenniscomp.data;

public class Player {
    private final int playerId;
    private final String name;
    private final String surname;
    private final String email;
    private final String birthDate;
    private final String gender;
    private final String phone;
    private final String username;
    private final String password;

    public Player(final int playerId, final String name, final String surname,
            final String email, final String birthDate, final String gender,
            final String phone, final String username, final String password) {
        this.playerId = playerId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public final class DAO {

    }
    
}
