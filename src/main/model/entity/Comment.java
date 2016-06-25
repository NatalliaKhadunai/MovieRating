package main.model.entity;

import java.sql.Date;

/**
 * Class {@code Comment} represents Comment entity.
 */

public class Comment {
    /** Film ID that identifies film, to which comment was left */
    private int FilmID;
    /** User login that identifies user, who left comment */
    private String userLogin;
    /** {@code Comment} content */
    private String content;
    /** Date, when comment was left */
    private Date date;

    /**
     * Returns film ID value for the object.
     * @return film ID value for the object.
     */
    public int getFilmID() {
        return FilmID;
    }

    /**
     * Sets film ID value for the object.
     * @param filmID value which is set in the field film ID.
     */
    public void setFilmID(int filmID) {
        FilmID = filmID;
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
     * Returns comment content value for the object.
     * @return comment content value for the object.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets comment content value for the object.
     * @param content value for the object.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns date value for the object.
     * @return date value for the object.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date value for the object.
     * @param date value for the object.
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
