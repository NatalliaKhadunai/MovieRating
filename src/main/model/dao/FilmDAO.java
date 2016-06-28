package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Film;
import main.model.entity.VideoProduct;
import main.FilmField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code FilmDAO} is a class, with the help of which data about films is extracted from database.
 * {@see main.model.entity.Film}
 */

public class FilmDAO extends AbstractDAO {

    /**
     * Search film by title.
     * @param title value.
     * @return Film entity.
     */
    public Film getEntity(String title) {
        if (title == null || title.equals("")) return null;
        Film film = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getEntityByName"));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            film = getSingleEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return film;
    }

    /**
     * Search film by ID.
     * @param ID value.
     * @return Film entity.
     */
    public Film getEntity(int ID) {
        if (ID <= 0) return null;
        Film film = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getEntityByID"));
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            film = getSingleEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return film;
    }

    /**
     * Search films by title.
     * @param title value.
     * @return List of Film entities.
     */
    public List<Film> getAllEntities(String title) {
        if (title == null || title.equals("")) return null;
        List<Film> filmList = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getAllEntitiesByTitle"));
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            filmList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return filmList;
    }

    /**
     * Return all Film entities.
     * @return List with Film entities.
     */
    public List<Film> getAllEntities() {
        List<Film> filmList = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getAllEntities"));
            ResultSet resultSet = preparedStatement.executeQuery();
            filmList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return filmList;
    }

    /**
     * Return top-rated films.
     * @return List with top-rated Film entities.
     */
    public List<Film> getTopRatedFilms() {
        List<Film> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getTopFilms"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setID(resultSet.getInt("ID"));
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                filmList.add(film);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return filmList;
    }

    /**
     * Return top-commented films.
     * @return List with top-commented Film entities.
     */
    public List<VideoProduct> getTopCommented() {
        List<VideoProduct> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getTopCommented"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
                film.setNumOfComments(resultSet.getInt("NumOfComments"));
                filmList.add(film);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return filmList;
    }

    /**
     * Return single entity from database result set.
     * @param resultSet incoming data set.
     * @return formed entity.
     */
    private Film getSingleEntity(ResultSet resultSet) {
        Film film = null;
        try {
            if (resultSet.next()) {
                film = new Film();
                film.setID(resultSet.getInt("ID"));
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return film;
    }

    /**
     * Return list of entities from database result set.
     * @param resultSet incoming data set.
     * @return formed entity.
     */
    private List<Film> getListEntity(ResultSet resultSet) {
        List<Film> filmList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Film film = new Film();
                film.setID(resultSet.getInt("ID"));
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
                filmList.add(film);
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return filmList;
    }

    /**
     * Add entity.
     * @param film entity to add.
     */
    public void addEntity(Film film) {
        if (film == null) return;
        try {
            PreparedStatement selectImageStatement = connection.prepareStatement(QueryManager.getProperty("selectImage"));
            selectImageStatement.setString(1, film.getPosterFileName());
            ResultSet resultSet = selectImageStatement.executeQuery();
            resultSet.next();
            int imageId = resultSet.getInt("ID");
            resultSet.close();
            selectImageStatement.close();
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.addEntity"));
            preparedStatement.setInt(1, imageId);
            preparedStatement.setString(2, film.getName());
            preparedStatement.setDate(3, film.getReleaseDate());
            preparedStatement.setString(4, film.getDescription());
            preparedStatement.setFloat(5, film.getRating());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Remove entity.
     * @param title value of entity to remove.
     */
    public void removeEntity(String title) {
        if (title == null || title.equals("")) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.removeEntity"));
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Update entity.
     * @param film entity to update.
     * @param listToUpdate fields to update.
     */
    public void updateEntity(Film film, List<FilmField> listToUpdate) {
        if (film == null || listToUpdate.size() == 0) return;
        try {
            String updateSet = "UPDATE film SET ";
            for (FilmField field : listToUpdate) {
                switch (field) {
                    case NAME: {
                        updateSet += "name='" + film.getName() + "',";
                    }
                    break;
                    case RELEASE_DATE: {
                        updateSet += "releaseDate='" + film.getReleaseDate() + "',";
                    }
                    break;
                    case DESCRIPTION: {
                        updateSet += "description='" + film.getDescription() + "',";
                    }
                    break;
                    case RATING: {
                        updateSet += "rating=" + film.getRating() + ",";
                    }
                    break;
                }
            }
            updateSet = updateSet.substring(0, updateSet.length() - 1);
            updateSet += " WHERE ID=" + film.getID();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSet);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Update film rating.
     * @param film entity to update.
     */
    public void updateRating(Film film) {
        if (film == null) return;
        MarkDAO markDAO = new MarkDAO();
        double avgMark = Math.rint(10.0 * (markDAO.averageMark(film))) / 10.0;
        markDAO.closeConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.updateRating"));
            preparedStatement.setDouble(1, avgMark);
            preparedStatement.setInt(2, film.getID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
