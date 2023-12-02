package service;

public class UserInfo {
    private int id_user;
    private String login;
    private String password;
    private String role;
    private String status;

    public UserInfo(int id_user, String login, String password, String role, String status) {
        this.id_user = id_user;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public int getId_user() {
        return id_user;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}
