package test;

import main.ProfileField;
import main.model.dao.UserDAO;
import main.model.entity.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserDAOTest {
    UserDAO userDAO = new UserDAO();

    @Test
    public void testGetEntityWithEmptyLogin() throws Exception {
        User user = userDAO.getEntity("");
        assertNull(user);
    }

    @Test
    public void testGetEntityWithNullLogin() throws Exception {
        User user = userDAO.getEntity(null);
        assertNull(user);
    }

    @Test
    public void testAddEntity() throws Exception {
        userDAO.addEntity(null);
    }

    @Test
    public void testUpdateEntity() throws Exception {
        userDAO.updateEntity(null, new ArrayList<ProfileField>());
    }

    @Test
    public void testChangeUserPasswordNullLogin() throws Exception {
        userDAO.changeUserPassword(null, 0);
    }

    @Test
    public void testChangeUserPasswordEmptyLogin() throws Exception {
        userDAO.changeUserPassword("", 0);
    }

    @Test
    public void testAddPoint() throws Exception {
        userDAO.addPoint(null, 0);
    }
}