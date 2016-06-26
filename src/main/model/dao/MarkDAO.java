package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Film;
import main.model.entity.Mark;
import main.model.entity.TVSeries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class {@code MaarkDAO} is a class, with the help of which data about marks is extracted from database.
 * {@see main.model.entity.Mark}
 */

public class MarkDAO extends AbstractDAO {
    /**
     * Add entity for film mark.
     * @param login represents user, who made comment.
     * @param film represents film, on which comment was made.
     * @param mark value.
     */
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

    /**
     * Return number of marks for given Film.
     * @param film represents Film entity.
     * @return number of marks for given Film.
     */
    public int numberOfMarks(Film film) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.numberOfMarks_Film"));
            preparedStatement.setInt(1, film.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("numOfMarks");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    /**
     * Return average mark for Film.
     * @param film represents Film entity.
     * @return average mark for Film.
     */
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

    /**
     * Search for mark.
     * @param login value, represents user who put mark.
     * @param film entity, represents film for which mark was put.
     * @return Mark entity.
     */
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

    /**
     * Add entity for tvseries mark.
     * @param login represents user, who made comment.
     * @param tvSeries represents tvseries, on which comment was made.
     * @param mark value.
     */
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

    /**
     * Return number of marks for given TVSeries.
     * @param tvSeries represents TVSeries entity.
     * @return number of marks for given TVSeries.
     */
    public int numberOfMarks(TVSeries tvSeries) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("markDAO.numberOfMarks_TVSeries"));
            preparedStatement.setInt(1, tvSeries.getID());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("numOMarks");
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return result;
    }

    /**
     * Return average mark for TVSeries.
     * @param tvSeries represents TVSeries entity.
     * @return average mark for TVSeries.
     */
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

    /**
     * Search for mark.
     * @param login value, represents user who put mark.
     * @param tvSeries entity, represents tvseries for which mark was put.
     * @return Mark entity.
     */
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
