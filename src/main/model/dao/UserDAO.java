package main.model.dao;

import main.model.manager.QueryManager;
import main.ProfileField;
import main.model.entity.Status;
import main.model.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO() {
        super();
    }

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
        finally {
            return user;
        }
    }

    public void addEntity(User user) {
        if (user == null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("userDAO.addEntity"));
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getPassword());
            preparedStatement.setDouble(4, Status.minimalStatus().lowerThreshold);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public void updateEntity(User user, List<ProfileField> listToUpdate) {
        if (user == null || listToUpdate.size() == 0) return;
        try {
            String updateSet = "UPDATE user SET ";
            for (ProfileField field : listToUpdate) {
                switch (field) {
                    case EMAIL: {
                        updateSet += "email='" + user.getEmail() + "' ";
                    }
                    break;
                    case FULL_NAME: {
                        updateSet += "fullName='" + user.getName() + "' ";
                    }
                    break;
                    case SEX: {
                        updateSet += "sex='" + user.getSex() + "' ";
                    }
                    break;
                }
            }
            updateSet += "WHERE login='" + user.getLogin() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSet);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

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

    public void addPoint(String login, double points) {
        if (login == null || login.equals("") || points == 0) return;
        User user = getEntity(login);
        Status userStatus = Status.defineStatus(user.getStatusCoefficient());
        if (userStatus != Status.ADMIN && userStatus != Status.BAN) {
            user.setStatusCoefficient(user.getStatusCoefficient() + points);
            updateStatusCoefficient(user);
            if (!user.getStatusName().equals(Status.defineStatus(user.getStatusCoefficient()).name())) {
                Status newStatus = Status.defineStatus(user.getStatusCoefficient());
                user.setStatusName(newStatus.name());
                updateStatus(user);
            }
        }
    }

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
        finally {
            return users;
        }
    }

    public void userStatusUp(User user) {
        if (user == null) return;
        Status maximumStatus = Status.maximumStatus();
        if (user.getStatusCoefficient() < maximumStatus.lowerThreshold) {
            Status newStatus = Status.upperStatus(Status.valueOf(user.getStatusName()));
            if (newStatus != null) {
                user.setStatusName(newStatus.name());
                user.setStatusCoefficient(newStatus.lowerThreshold);
                updateStatusCoefficient(user);
                updateStatus(user);
            }
        }
    }

    public void userStatusUp(String login) {
        if (login == null || login.equals("")) return;
        User user = getEntity(login);
        userStatusUp(user);
    }

    public void userStatusDown(User user) {
        if (user == null) return;
        Status minimalStatus = Status.minimalStatus();
        if (user.getStatusCoefficient() > minimalStatus.upperThreshold) {
            Status newStatus = Status.lowerStatus(Status.valueOf(user.getStatusName()));
            if (newStatus != null) {
                user.setStatusName(newStatus.name());
                user.setStatusCoefficient(newStatus.lowerThreshold);
                updateStatusCoefficient(user);
                updateStatus(user);
            }
        }
    }

    public void userStatusDown(String login) {
        if (login == null || login.equals("")) return;
        User user = getEntity(login);
        userStatusDown(user);
    }

    public void userBan(User user) {
        if (user == null) return;
        user.setStatusName(Status.banedStatus().name());
        updateStatus(user);
    }

    public void userBan(String login) {
        if (login == null || login.equals("")) return;
        User user = getEntity(login);
        userBan(user);
    }

    public void userRemoveBan(User user) {
        if (user == null) return;
        Status currentStatus =  Status.defineStatus(user.getStatusCoefficient());
        user.setStatusName(currentStatus.name());
        updateStatus(user);
    }

    public void userRemoveBan(String login) {
        if (login == null || login.equals("")) return;
        User user = getEntity(login);
        userRemoveBan(user);
    }

    private void updateStatusCoefficient(User user) {
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

    private void updateStatus(User user) {
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

    private String defineProfilePhotoBySex(String sex) {
        if (sex == null || sex.equals("")) return null;
        String profilePhoto = null;
        if (sex.equals("F")) profilePhoto = "default-female.png";
        else if (sex.equals("M")) profilePhoto = "default-male.png";
        else profilePhoto = "default-user.png";
        return profilePhoto;
    }

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
                user.setStatusName(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            return user;
        }
    }

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
        finally {
            return users;
        }
    }
}
