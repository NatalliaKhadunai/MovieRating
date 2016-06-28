package main.model.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Class {@code Comment} represents Comment entity.
 */

public class Comment {
    /**
     * Film ID that identifies film, to which comment was left
     * {@see main.model.entity.Film}
     * */
    private int FilmID;
    /**
     * User login that identifies user, who left comment
     * {@see main.model.entity.User}
     * */
    private String userLogin;
    /** {@code Comment} content */
    private String content;
    /** {@link java.sql.Date}, when comment was left */
    private Timestamp date;

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
    public Timestamp getDate() {
        return date;
    }

    /**
     * Sets date value for the object.
     * @param date value for the object.
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (FilmID != comment.FilmID) return false;
        if (!userLogin.equals(comment.userLogin)) return false;
        if (!content.equals(comment.content)) return false;
        return date.equals(comment.date);

    }

    @Override
    public int hashCode() {
        int result = FilmID;
        result = 31 * result + userLogin.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
