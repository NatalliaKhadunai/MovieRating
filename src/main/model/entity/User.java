package main.model.entity;

public class User {
    private String login;
    private String email;
    private String name;
    private int password;
    private String profilePhoto;
    private double statusCoefficient;
    private String statusName;
    private char sex;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public double getStatusCoefficient() {
        return statusCoefficient;
    }

    public void setStatusCoefficient(double statusCoefficient) {
        this.statusCoefficient = statusCoefficient;
    }

    public String getSex() {
        return Character.valueOf(sex).toString();
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}
