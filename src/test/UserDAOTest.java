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
    public void testAddPointNullLogin() throws Exception {
        userDAO.addPoint(null, 0);
    }

    @Test
    public void testAddPointEmptyLogin() throws Exception {
        userDAO.addPoint("", 0);
    }

    @Test
    public void testUserStatusUpNullUser() throws Exception {
        User user = null;
        userDAO.userStatusUp(user);
    }

    @Test
    public void testUserStatusUpNullLogin() throws Exception {
        String login = null;
        userDAO.userStatusUp(login);
    }

    @Test
    public void testUserStatusUpEmptyLogin() throws Exception {
        userDAO.userStatusUp("");
    }

    @Test
    public void testUserStatusDownNullUser() throws Exception {
        User user = null;
        userDAO.userStatusDown(user);
    }

    @Test
    public void testUserStatusDownNullLogin() throws Exception {
        String login = null;
        userDAO.userStatusDown(login);
    }

    @Test
    public void testUserStatusDownEmptyLogin() throws Exception {
        userDAO.userStatusDown("");
    }

    @Test
    public void testUserBanNullUser() throws Exception {
        User user = null;
        userDAO.userBan(user);
    }

    @Test
    public void testUserBanNullLogin() throws Exception {
        String login = null;
        userDAO.userBan(login);
    }

    @Test
    public void testUserBanEmptyLogin() throws Exception {
        userDAO.userBan("");
    }

    @Test
    public void testUserRemoveBanNullUser() throws Exception {
        User user = null;
        userDAO.userRemoveBan(user);
    }

    @Test
    public void testUserRemoveBanNullLogin() throws Exception {
        String login = null;
        userDAO.userRemoveBan(login);
    }

    @Test
    public void testUserRemoveBanEmptyLogin() throws Exception {
        userDAO.userRemoveBan("");
    }
}