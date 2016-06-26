package test;

import main.model.dao.ImageDAO;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageDAOTest {
    ImageDAO imageDAO = new ImageDAO();

    @Test
    public void testAddEntityNullFileName() throws Exception {
        imageDAO.addEntity(null);
    }

    @Test
    public void testAddEntityEmptyFileName() throws Exception {
        imageDAO.addEntity("");
    }

    @Test
    public void testRemoveHomeSliderImageNullPath() throws Exception {
        imageDAO.removeHomeSliderImage(null);
    }

    @Test
    public void testRemoveHomeSliderImageEmptyPath() throws Exception {
        imageDAO.removeHomeSliderImage("");
    }
}