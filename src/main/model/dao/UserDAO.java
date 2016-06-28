package main.model.dao;

import main.model.manager.DefaultImageManager;
import main.model.manager.QueryManager;
import main.ProfileField;
import main.model.entity.Status;
import main.model.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code UserDAO} is a class, with the help of which data about users is extracted from database.
 * {@see main.model.entity.User}
 */

public class UserDAO extends AbstractDAO {

    public UserDAO() {
        super();
    }

    /**
     * Search entity by login.
     * @param login value.
     * @return found User entity.
     */
    public User getEntity(String login) {
        if (login == null || login.equals("")) return null;
        User user = null;
        try {
            PreparedStatement selectUserStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.getEntity"));
            selectUserStatement.setString(1, login);
            ResultSet resultSet = selectUserStatement.executeQuery();
            user = getSingleEntity(resultSet);
            resultSet.close();
            selectUserStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    /**
     * Add entity.
     * @param user entity to add.
     */
    public void addEntity(User user) {
        if (user == null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.addEntity"));
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getPassword());
            preparedStatement.setDouble(4, Status.minimalStatus().lowerThreshold);
            preparedStatement.setInt(5, Status.minimalStatus().ordinal() + 1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            user.setProfilePhoto(defineProfilePhotoBySex(user.getSex()));
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public void removeEntity(String login) {
        if (login == null || login.equals("")) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.removeEntity"));
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Update entity.
     * @param user entity to update.
     * @param listToUpdate fields to update.
     */
    public void updateEntity(User user, List<ProfileField> listToUpdate) {
        if (user == null || listToUpdate.size() == 0) return;
        try {
            String updateSet = "UPDATE user SET ";
            for (ProfileField field : listToUpdate) {
                switch (field) {
                    case EMAIL: {
                        updateSet += "email='" + user.getEmail() + "',";
                    }
                    break;
                    case FULL_NAME: {
                        updateSet += "fullName='" + user.getName() + "',";
                    }
                    break;
                    case SEX: {
                        updateSet += "sex='" + user.getSex() + "',";
                    }
                    break;
                    case PROFILE_PHOTO: {
                        updateSet += "profilePhoto='" + user.getProfilePhoto() + "',";
                    }
                    break;
                }
            }
            updateSet = updateSet.substring(0, updateSet.length() - 1);
            updateSet += " WHERE login='" + user.getLogin() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSet);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Change user password.
     * @param login value, that identifies User entity.
     * @param passwordHash value.
     */
    public void changeUserPassword(String login, int passwordHash) {
        if (login == null || login.equals("") || passwordHash<=0) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.changeUserPassword"));
            preparedStatement.setInt(1, passwordHash);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Return all User entities.
     * @return List with User entities.
     */
    public List<User> getAllEntities() {
        List<User> users = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.getAllEntities"));
            ResultSet resultSet = preparedStatement.executeQuery();
            users = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    /**
     * Return number of users in system.
     * @return number of users in system.
     */
    public int getNumOfUsers() {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.getNumOfUsers"));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("numOfUsers");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    /**
     * Add points to user status coefficient.
     * @param user entity, that identifies logged user.
     * @param points value to add.
     */
    public void addPoint(User user, double points) {
        if (user == null || points == 0) return;
        if (user.getStatusCoefficient() == Status.minimalStatus().lowerThreshold && points==-0.1) return;
        if (user.getStatusCoefficient() == Status.maximumStatus().upperThreshold && points==0.1) return;
        user.setStatusCoefficient(Math.rint(10.0 * (user.getStatusCoefficient() + points)) / 10.0);
        updateStatusCoefficient(user);
        if (!user.getStatusName().equals(Status.defineStatus(user.getStatusCoefficient()).name()) &&
                !user.getStatusName().equals(Status.adminStatus().name())) {
            Status newStatus = Status.defineStatus(user.getStatusCoefficient());
            user.setStatusName(newStatus.name());
            updateStatus(user);
        }
    }

    /**
     * Update user status coefficient.
     * @param user entity, which status coefficient should be updated.
     */
    public void updateStatusCoefficient(User user) {
        if (user == null) return;
        try {
            PreparedStatement updateCoefficient = connection.prepareStatement(QueryManager.getProperty("userDAO.updateStatusCoefficient"));
            updateCoefficient.setDouble(1, user.getStatusCoefficient());
            updateCoefficient.setString(2, user.getLogin());
            updateCoefficient.executeUpdate();
            updateCoefficient.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Update user status.
     * @param user entity, which status should be updated.
     */
    public void updateStatus(User user) {
        if (user == null) return;
        try {
            PreparedStatement updateStatus = connection.prepareStatement(QueryManager.getProperty("userDAO.updateStatus"));
            updateStatus.setString(1, user.getStatusName());
            updateStatus.setString(2, user.getLogin());
            updateStatus.executeUpdate();
            updateStatus.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Define user profile photo by sex.
     * @param sex value.
     * @return profile photo filename.
     */
    private String defineProfilePhotoBySex(String sex) {
        if (sex == null || sex.equals("")) return null;
        String profilePhoto = null;
        switch (sex) {
            case "F": profilePhoto = DefaultImageManager.getProperty("defaultFemale"); break;
            case "M": profilePhoto = DefaultImageManager.getProperty("defaultMale"); break;
            default: profilePhoto = DefaultImageManager.getProperty("defaultUser"); break;
        }
        return profilePhoto;
    }

    /**
     * Invoke user info from ResutSet.
     * @param resultSet from which data will be invoked.
     * @return User entity.
     */
    private User getSingleEntity(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setLogin(resultSet.getString("Login"));
                user.setEmail(resultSet.getString("Email"));
                user.setName(resultSet.getString("FullName"));
                user.setPassword(resultSet.getInt("Password"));
                user.setProfilePhoto(resultSet.getString("ProfilePhoto"));
                String sex = resultSet.getString("Sex");
                if (sex != null) user.setSex(sex.charAt(0));
                if (user.getProfilePhoto() == null) user.setProfilePhoto(defineProfilePhotoBySex(user.getSex()));
                user.setStatusCoefficient(resultSet.getDouble("StatusCoefficient"));
                String statusName = resultSet.getString("Name");
                user.setStatusName(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    /**
     * Form List with User entities.
     * @param resultSet from which data will be invoked.
     * @return List with User entities.
     */
    private List<User> getListEntity(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("Login"));
                user.setEmail(resultSet.getString("Email"));
                user.setName(resultSet.getString("FullName"));
                user.setPassword(resultSet.getInt("Password"));
                user.setProfilePhoto(resultSet.getString("ProfilePhoto"));
                String sex = resultSet.getString("Sex");
                if (sex != null) user.setSex(sex.charAt(0));
                if (user.getProfilePhoto() == null) user.setProfilePhoto(defineProfilePhotoBySex(user.getSex()));
                user.setStatusCoefficient(resultSet.getDouble("StatusCoefficient"));
                user.setStatusName(resultSet.getString("Name"));
                users.add(user);
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }
}
