package test;

import main.model.dao.MarkDAO;
import main.model.entity.Film;
import main.model.entity.Mark;
import main.model.entity.TVSeries;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarkDAOTest {
    MarkDAO markDAO = new MarkDAO();

    @Test
    public void testAddEntityFilm() throws Exception {
        String login = null;
        Film film = null;
        int mark = 0;
        markDAO.addEntity(login, film, mark);
    }

    @Test
    public void testAddEntityTVSeries() throws Exception {
        String login = null;
        TVSeries tvSeries = null;
        int mark = 0;
        markDAO.addEntity(login, tvSeries, mark);
    }

    @Test
    public void testNumberOfMarksFilm() throws Exception {
        Film film = null;
        int numOfMarks = markDAO.numberOfMarks(film);
        assertEquals(numOfMarks, -1);
    }

    @Test
    public void testNumberOfMarksTVSeries() throws Exception {
        TVSeries tvSeries = null;
        int numOfMarks = markDAO.numberOfMarks(tvSeries);
        assertEquals(numOfMarks, -1);
    }

    @Test
    public void testAverageMarkFilm() throws Exception {
        Film film = null;
        double avgMark = markDAO.averageMark(film);
        assertEquals(avgMark, -1, 0);
    }

    @Test
    public void testAverageMarkTVSeries() throws Exception {
        TVSeries tvSeries = null;
        double avgMark = markDAO.averageMark(tvSeries);
        assertEquals(avgMark, -1, 0);
    }

    @Test
    public void testGetEntityFilmMark() throws Exception {
        String login = null;
        Film film = null;
        Mark mark = markDAO.getEntity(login, film);
        assertNull(mark);
    }

    @Test
    public void testGetEntityTVSeriesMark() throws Exception {
        String login = null;
        TVSeries tvSeries = null;
        Mark mark = markDAO.getEntity(login, tvSeries);
        assertNull(mark);
    }
}