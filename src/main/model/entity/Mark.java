package main.model.entity;

/**
 * Class {@code Mark} represents Mark entity.
 */

public class Mark {
    /** Film ID that identifies film, to which comment was left */
    protected int filmID;
    /** User login that identifies user, who left comment */
    protected String userLogin;
    /** {@code Mark} mark */
    protected int mark;

    /**
     * Returns film ID value for the object.
     * @return film ID value for the object.
     */
    public int getFilmID() {
        return filmID;
    }

    /**
     * Sets film ID value for the object.
     * @param filmID value which is set in the field film ID.
     */
    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    /**
     * Returns user login value for the object.
     * @return user login value for the object.
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets user login value for the object.
     * @param userLogin value which is set in the field user login.
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Returns mark value for the object.
     * @return mark value for the object.
     */
    public int getMark() {
        return mark;
    }

    /**
     * Sets mark value for the object.
     * @param mark value which is set in the field mark.
     */
    public void setMark(int mark) {
        this.mark = mark;
    }
}
