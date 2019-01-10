package fantasticcouscous.users.model;

public class UserData {

    private String login;
    private String firstName;
    //private String lastName;
    //private Set<String> groupSet;

    public UserData(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
