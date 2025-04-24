package tenniscomp.data;

public class Referee {
    private final int refereeId;
    private final String name;
    private final String surname;
    private final String email;
    private final String birthDate;
    private final String gender;
    private final String phone;
    private final String username;
    private final String password;

    public Referee(int refereeId, String name, String surname,
            String email, String birthDate, String gender,
            String phone, String username, String password) {
        this.refereeId = refereeId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public int getRefereeId() {
        return refereeId;
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
