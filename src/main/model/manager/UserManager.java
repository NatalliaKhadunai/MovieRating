package main.model.manager;

import main.model.dao.UserDAO;
import main.model.entity.Status;
import main.model.entity.User;
import org.apache.log4j.Logger;

/**
 * Class {@code UserManager} used for user status management.
 */

public class UserManager {
    private final static Logger logger = Logger.getLogger("ManagerLogger");
    /**
     * Increase user status.
     * @param user entity represents user, which status should be increased.
     */
    public void userStatusUp(User user) {
        if (user == null) return;
        Status maximumStatus = Status.maximumStatus();
        if (user.getStatusCoefficient() < maximumStatus.lowerThreshold) {
            Status newStatus = Status.upperStatus(Status.valueOf(user.getStatusName()));
            updateStatus(user, newStatus);
        }
    }

    /**
     * Increase user status.
     * @param login value represents login of user, which status should be increased.
     */
    public void userStatusUp(String login) {
        if (login == null || login.equals("")) return;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        userStatusUp(user);
    }

    /**
     * Decrease user status.
     * @param user entity represents user, which status should be decreased.
     */
    public void userStatusDown(User user) {
        if (user == null) return;
        Status minimalStatus = Status.minimalStatus();
        if (user.getStatusCoefficient() > minimalStatus.upperThreshold) {
            Status newStatus = Status.lowerStatus(Status.valueOf(user.getStatusName()));
            updateStatus(user, newStatus);
        }
    }

    /**
     * Update user status and status coefficient in database.
     * @param user entity to update.
     * @param newStatus users new status.
     */
    private void updateStatus(User user, Status newStatus) {
        if (newStatus != null) {
            user.setStatusName(newStatus.name());
            user.setStatusCoefficient(newStatus.lowerThreshold);
            UserDAO userDAO = new UserDAO();
            userDAO.updateStatusCoefficient(user);
            userDAO.updateStatus(user);
            userDAO.closeConnection();
        }
    }

    /**
     * Decrease user status.
     * @param login value represents login of user, which status should be decreased.
     */
    public void userStatusDown(String login) {
        if (login == null || login.equals("")) return;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        userStatusDown(user);
    }

    /**
     * Ban user.
     * @param user entity represents user, which should be baned.
     */
    public void userBan(User user) {
        if (user == null || user.getStatusName().equals(Status.adminStatus().name())) return;
        user.setStatusName(Status.banedStatus().name());
        UserDAO userDAO = new UserDAO();
        userDAO.updateStatus(user);
        userDAO.closeConnection();
    }

    /**
     * Ban user.
     * @param login value represents login of user, which should be baned.
     */
    public void userBan(String login) {
        if (login == null || login.equals("")) return;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        userBan(user);
    }

    /**
     * Remove user ban.
     * @param user entity represents user, which ban should be removed.
     */
    public void userRemoveBan(User user) {
        if (user == null || !user.getStatusName().equals(Status.banedStatus().name())) return;
        Status currentStatus =  Status.defineStatus(user.getStatusCoefficient());
        user.setStatusName(currentStatus.name());
        UserDAO userDAO = new UserDAO();
        userDAO.updateStatus(user);
        userDAO.closeConnection();
    }

    /**
     * Remove user ban.
     * @param login value represents login of user, which ban should be removed.
     */
    public void userRemoveBan(String login) {
        if (login == null || login.equals("")) return;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        userRemoveBan(user);
    }

    public void userMakeAdmin(User user) {
        if (user == null || user.getStatusName().equals(Status.banedStatus().name())) return;
        user.setStatusName(Status.adminStatus().name());
        UserDAO userDAO = new UserDAO();
        userDAO.updateStatus(user);
        userDAO.closeConnection();
    }

    public void userMakeAdmin(String login) {
        if (login == null || login.equals("")) return;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        userMakeAdmin(user);
    }

    public void removeAdminStatus(String login) {
        if (login == null || login.equals("")) return;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        removeAdminStatus(user);
    }

    public void removeAdminStatus(User user) {
        if (user == null || !user.getStatusName().equals(Status.adminStatus().name())) return;
        Status currentStatus =  Status.defineStatus(user.getStatusCoefficient());
        user.setStatusName(currentStatus.name());
        UserDAO userDAO = new UserDAO();
        userDAO.updateStatus(user);
        userDAO.closeConnection();
    }
}
