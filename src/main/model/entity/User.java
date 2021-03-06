package main.model.entity;

/**
 * Class {@code User} represents User entity.
 */

public class User {
    /** The value is used to identify {@code User} object */
    private String login;
    /** {@code User} email */
    private String email;
    /** {@code User} name */
    private String name;
    /** {@code User} password hash-code */
    private int password;
    /** {@code User} profile photo filename */
    private String profilePhoto;
    /** {@code User} status coefficient */
    private double statusCoefficient;
    /** {@code User} status */
    private Status status;
    /** {@code User} sex */
    private char sex;

    /**
     * Returns login value for the object.
     * @return login value for the object.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login value for the object.
     * @param login value which is set in the field login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns email value for the object.
     * @return email value for the object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email value for the object.
     * @param email value which is set in the field email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns name value for the object.
     * @return name value for the object.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name value for the object.
     * @param name value which is set in the field name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns password hash-code value for the object.
     * @return password hash-code value for the object.
     */
    public int getPassword() {
        return password;
    }

    /**
     * Sets password hash-code value for the object.
     * @param password value which is set in the field password.
     */
    public void setPassword(int password) {
        this.password = password;
    }

    /**
     * Returns profile photo filename value for the object.
     * @return profile photo filename value for the object.
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * Sets profile photo filename value for the object.
     * @param profilePhoto value which is set in the field profile photo.
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * Returns status name value for the object.
     * @return status name value for the object.
     */
    public String getStatusName() {
        return status.name();
    }

    /**
     * Sets status value for the object.
     * @param statusName value which is set in the field status.
     */
    public void setStatusName(String statusName) {
        this.status = Status.valueOf(statusName);
    }

    /**
     * Returns status coefficient value for the object.
     * @return status coefficient value for the object.
     */
    public double getStatusCoefficient() {
        return statusCoefficient;
    }

    /**
     * Sets status coefficient for the object.
     * @param statusCoefficient value which is set in the field status coefficient.
     */
    public void setStatusCoefficient(double statusCoefficient) {
        this.statusCoefficient = statusCoefficient;
    }

    /**
     * Returns sex value for the object.
     * @return sex value for the object.
     */
    public String getSex() {
        return Character.valueOf(sex).toString();
    }

    /**
     * Sets sex value for the object.
     * @param sex value which is set in the field sex.
     */
    public void setSex(char sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (password != user.password) return false;
        if (Double.compare(user.statusCoefficient, statusCoefficient) != 0) return false;
        if (sex != user.sex) return false;
        if (!login.equals(user.login)) return false;
        if (!email.equals(user.email)) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (profilePhoto != null ? !profilePhoto.equals(user.profilePhoto) : user.profilePhoto != null) return false;
        return status == user.status;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = login.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + password;
        result = 31 * result + (profilePhoto != null ? profilePhoto.hashCode() : 0);
        temp = Double.doubleToLongBits(statusCoefficient);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + status.hashCode();
        result = 31 * result + (int) sex;
        return result;
    }
}
