package test;

import main.FilmField;
import main.model.dao.FilmDAO;
import main.model.entity.Film;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FilmDAOTest {
    FilmDAO filmDAO = new FilmDAO();

    @Test
    public void testGetEntityNullTitle() throws Exception {
        Film film = filmDAO.getEntity(null);
        assertNull(film);
    }

    @Test
    public void testGetEntityEmptyTitle() throws Exception {
        Film film = filmDAO.getEntity("");
        assertNull(film);
    }

    @Test
    public void testGetEntityByID() throws Exception {
        Film film = filmDAO.getEntity(-1);
        assertNull(film);
    }

    @Test
    public void testAddEntity() throws Exception {
        Film film = null;
        filmDAO.addEntity(film);
    }

    @Test
    public void testRemoveEntityNullTitle() throws Exception {
        String title = null;
        filmDAO.removeEntity(title);
    }

    @Test
    public void testRemoveEntityEmptyTitle() throws Exception {
        filmDAO.removeEntity("");
    }

    @Test
    public void testUpdateEntityNullFilm() throws Exception {
        filmDAO.updateEntity(null, new ArrayList<>());
    }

    @Test
    public void testUpdateEntityZeroFieldsToUpdate() throws Exception {
        filmDAO.updateEntity(new Film(), new ArrayList<>());
    }
}