package test;

import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TVSeriesDAOTest {
    TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();

    @Test
    public void testGetEntityNullTitle() throws Exception {
        TVSeries tvSeries = tvSeriesDAO.getEntity(null);
        assertNull(tvSeries);
    }

    @Test
    public void testGetEntityEmptyTitle() throws Exception {
        TVSeries tvSeries = tvSeriesDAO.getEntity("");
        assertNull(tvSeries);
    }

    @Test
    public void testGetEntityByID() throws Exception {
        TVSeries tvSeries = tvSeriesDAO.getEntity(-1);
        assertNull(tvSeries);
    }

    @Test
    public void testAddEntity() throws Exception {
        TVSeries tvSeries = null;
        tvSeriesDAO.addEntity(tvSeries);
    }

    @Test
    public void testGetAllEntities() throws Exception {

    }

    @Test
    public void testRemoveEntityNullTitle() throws Exception {
        tvSeriesDAO.removeEntity(null);
    }

    @Test
    public void testRemoveEntityEmptyTitle() throws Exception {
        tvSeriesDAO.removeEntity("");
    }

    @Test
    public void testUpdateEntityNullFilm() throws Exception {
        tvSeriesDAO.updateEntity(null, new ArrayList<>());
    }

    @Test
    public void testUpdateEntityZeroFieldsToUpdate() throws Exception {
        tvSeriesDAO.updateEntity(new TVSeries(), new ArrayList<>());
    }

    @Test
    public void testUpdateRating() throws Exception {
        tvSeriesDAO.updateRating(null);
    }
}