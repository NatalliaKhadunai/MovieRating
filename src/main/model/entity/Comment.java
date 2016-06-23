package main.model.entity;

import java.sql.Date;

public class Comment {
    private int FilmID;
    private String userLogin;
    private String content;
    private Date date;

    public int getFilmID() {
        return FilmID;
    }

    public void setFilmID(int filmID) {
        FilmID = filmID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
