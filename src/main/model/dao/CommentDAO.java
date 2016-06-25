package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Comment;
import main.model.entity.Film;
import main.model.entity.TVSeries;
import main.controller.RequestType;

import java.sql.*;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class {@code CommentDAO} is a class, with the help of which data about comments is extracted from database.
 */

public class CommentDAO extends AbstractDAO {

    public CommentDAO() {
        super();
    }

    /**
     * Searches for all the comments made on given TVSeries
     * @param tvSeries entity, for which comments are sought.
     * @return list of found comments.
     */
    public List<Comment> getAllEntities(TVSeries tvSeries) {
        List<Comment> commentList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getAllEntities_TVSeries"));
            preparedStatement.setString(1, tvSeries.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            commentList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return commentList;
        }
    }

    /**
     * Searches for all the comments made on given TVSeries
     * @param film entity, for which comments are sought.
     * @return list of found comments.
     */
    public List<Comment> getAllEntities(Film film) {
        List<Comment> commentList = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(QueryManager.getProperty("commentDAO.getAllEntities_Film"));
            preparedStatement.setString(1, film.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            commentList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return commentList;
        }
    }

    /**
     * Return List of comments data taken from given ResultSet.
     * @param resultSet value is a set of data to be disassemble.
     * @return list of comments.
     */
    private List<Comment> getListEntity(ResultSet resultSet) {
        List<Comment> commentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setUserLogin(resultSet.getString("UserLogin"));
                comment.setContent(resultSet.getString("Content"));
                comment.setDate(resultSet.getDate("Date"));
                commentList.add(comment);
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return commentList;
    }

    /**
     * Add entity for film comment.
     * @param film represents film, on which comment was made.
     * @param login represents user, who made comment.
     * @param content represents content of the comment.
     * @param date represents date, when comment was made.
     */
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

    /**
     * Add entity for film comment.
     * @param tvSeries represents tvSeries, on which comment was made.
     * @param login represents user, who made comment.
     * @param content represents content of the comment.
     * @param date represents date, when comment was made.
     */
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

    /**
     * Search for comments, that were made after given timestamp.
     * @param film represents film, for which search is made.
     * @param date represents time border.
     * @return String value in XML-format, that contains info about found comments.
     */
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

    /**
     * Search for comments, that were made after given timestamp.
     * @param tvSeries represents TV Series, for which search is made.
     * @param date represents time border.
     * @return String value in XML-format, that contains info about found comments.
     */
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

    /**
     *
     * @param resultSet
     * @return
     */
    private String commentsToXML(ResultSet resultSet) {
        String result = "<?xml version=\"1.0\"?><comments>";
        try {
            while (resultSet.next()) {
                result += "<comment>";
                result += "<userLogin>" + resultSet.getString("UserLogin") + "</userLogin>";
                result += "<content>" + resultSet.getString("Content") + "</content>";
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
                DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH);
                String formattedDate = df.format(resultSet.getTimestamp("Date"));
                String formattedTime = tf.format(resultSet.getTimestamp("Date"));
                result += "<date>" + formattedDate + " " + formattedTime + "</date>";
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
