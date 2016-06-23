package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Comment;
import main.model.entity.Film;
import main.model.entity.TVSeries;
import main.controller.RequestType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends AbstractDAO<Comment> {

    public CommentDAO() {
        super();
    }

    public List<Comment> getAllEntities(String title, RequestType requestType) {
        List<Comment> commentList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            switch (requestType) {
                case FILM_PAGE:
                    preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getAllEntities_Film"));
                    break;
                case TVSERIES_PAGE:
                    preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getAllEntities_TVSeries"));
                    break;
            }
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Comment comment = new Comment();
                comment.setUserLogin(resultSet.getString("UserLogin"));
                comment.setContent(resultSet.getString("Content"));
                comment.setDate(resultSet.getDate("Date"));
                commentList.add(comment);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return commentList;
        }
    }

    public void addEntity(Film film, String login, String content, Timestamp date) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.addEntity_Film"));
            preparedStatement.setInt(1, film.getID());
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, content);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public void addEntity(TVSeries tvSeries, String login, String content, Timestamp date) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.addEntity_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, content);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public String getLatestComments(Film film, Timestamp date) {
        String result = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getLatestComments_Film"));
            preparedStatement.setString(1, date.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            result = commentsToXML(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return result;
        }
    }

    public String getLatestComments(TVSeries tvSeries, Timestamp date) {
        String result = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getLatestComments_TVSeries"));
            preparedStatement.setString(1, date.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            result = commentsToXML(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return result;
        }
    }

    private String commentsToXML(ResultSet resultSet) {
        String result = "<?xml version=\"1.0\"?><comments>";
        try {
            while (resultSet.next()) {
                result += "<comment>";
                result += "<userLogin>" + resultSet.getString("UserLogin") + "</userLogin>";
                result += "<content>" + resultSet.getString("Content") + "</content>";
                result += "<date>" + resultSet.getDate("Date") + "</date>";
                result += "</comment>";
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        result += "</comments>";
        return result;
    }
}
