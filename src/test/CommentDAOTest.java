package test;

import main.model.dao.CommentDAO;
import main.model.entity.Comment;
import main.model.entity.Film;
import main.model.entity.TVSeries;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class CommentDAOTest {
    CommentDAO commentDAO = new CommentDAO();

    @Test
    public void testGetAllEntitiesFilm() throws Exception {
        Film film = null;
        List<Comment> list = commentDAO.getAllEntities(film);
        assertNull(list);
    }

    @Test
    public void testGetAllEntitiesTVSeries() throws Exception {
        TVSeries tvSeries = null;
        List<Comment> list = commentDAO.getAllEntities(tvSeries);
        assertNull(list);
    }

    @Test
    public void testAddEntityNullFieldsFilm() throws Exception {
        Film film = null;
        String login = null;
        String content = null;
        Timestamp date = null;
        commentDAO.addEntity(film, login, content, date);
    }

    @Test
    public void testAddEntityNullFieldsTVSeries() throws Exception {
        TVSeries tvSeries = null;
        String login = null;
        String content = null;
        Timestamp date = null;
        commentDAO.addEntity(tvSeries, login, content, date);
    }

    @Test
    public void testGetLatestFilmComments() throws Exception {
        Film film = null;
        Timestamp date = null;
        Locale locale = null;
        String str = commentDAO.getLatestComments(film, date, locale);
        assertNull(str);
    }

    @Test
    public void testGetLatestTVSeriesComments() throws Exception {
        TVSeries tvSeries = null;
        Timestamp date = null;
        Locale locale = null;
        String str = commentDAO.getLatestComments(tvSeries, date, locale);
        assertNull(str);
    }
}