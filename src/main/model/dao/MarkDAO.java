package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Film;
import main.model.entity.Mark;
import main.model.entity.TVSeries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkDAO extends AbstractDAO<Mark> {
    public void addEntity(String login, Film film, int mark) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.addEntity_Film"));
            preparedStatement.setInt(1, film.getID());
            preparedStatement.setString(2, login);
            preparedStatement.setInt(3, mark);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public int numberOfComments(Film film) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.numberOfComments_Film"));
            preparedStatement.setInt(1, film.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("numOfComments");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    public float averageMark(Film film) {
        float result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.averageMark_Film"));
            preparedStatement.setInt(1, film.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getFloat("avgMark");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    public Mark getEntity(String login, Film film) {
        Mark mark = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.getEntity_Film"));
            preparedStatement.setInt(1, film.getID());
            preparedStatement.setString(2, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mark = new Mark();
                mark.setFilmID(resultSet.getInt("FilmID"));
                mark.setUserLogin(resultSet.getString("UserLogin"));
                mark.setMark(resultSet.getInt("Mark"));
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return mark;
        }
    }

    public void addEntity(String login, TVSeries tvSeries, int mark) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.addEntity_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            preparedStatement.setString(2, login);
            preparedStatement.setInt(3, mark);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public int numberOfComments(TVSeries tvSeries) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.numberOfComments_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("numOfComments");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    public float averageMark(TVSeries tvSeries) {
        float result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.averageMark_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getFloat("avgMark");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    public Mark getEntity(String login, TVSeries tvSeries) {
        Mark mark = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.getEntity_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            preparedStatement.setString(2, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mark = new Mark();
                mark.setFilmID(resultSet.getInt("TVSeriesID"));
                mark.setUserLogin(resultSet.getString("UserLogin"));
                mark.setMark(resultSet.getInt("Mark"));
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return mark;
        }
    }
}
