package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.Film;
import main.model.entity.VideoProduct;
import main.FilmField;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO extends AbstractDAO<Film> {

    public FilmDAO() {
        super();
    }

    public Film getEntity(String title) {
        if (title == null || title.equals("")) return null;
        Film film = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getEntityByName"));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film = new Film();
                film.setID(resultSet.getInt("ID"));
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return film;
        }
    }

    public Film getEntity(int ID) {
        if (ID <= 0) return null;
        Film film = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getEntityByID"));
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film = new Film();
                film.setID(resultSet.getInt("ID"));
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return film;
        }
    }

    public List<Film> getAllEntities(String title) {
        if (title == null || title.equals("")) return null;
        List<Film> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getAllEntitiesByTitle"));
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
                filmList.add(film);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return filmList;
        }
    }

    public List<Film> getAllEntities() {
        List<Film> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getAllEntities"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setName(resultSet.getString("Name"));
                film.setReleaseDate(resultSet.getDate("ReleaseDate"));
                film.setDescription(resultSet.getString("Description"));
                film.setRating(resultSet.getFloat("Rating"));
                film.setPosterFileName(resultSet.getString("FileName"));
                filmList.add(film);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return filmList;
        }
    }

    public List<Film> getTopFilms() {
        List<Film> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getTopFilms"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
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
        finally {
            return filmList;
        }
    }

    public List<VideoProduct> getTopCommented() {
        List<VideoProduct> filmList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("filmDAO.getTopCommented"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setName(resultSet.getString("Name"));
                Reader reader = resultSet.getCharacterStream("Name");
                int ch = 0;
                while ((ch = reader.read()) != -1) {
                    int c = ch;
                }
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
        finally {
            return filmList;
        }
    }

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

    public void updateEntity(Film film, List<FilmField> listToUpdate) {
        if (film == null || listToUpdate.size() == 0) return;
        try {
            String updateSet = "UPDATE film SET ";
            for (FilmField field : listToUpdate) {
                switch (field) {
                    case NAME: {
                        updateSet += "name='" + film.getName() + "' ";
                    }
                    break;
                    case RELEASE_DATE: {
                        updateSet += "releaseDate='" + film.getReleaseDate() + "' ";
                    }
                    break;
                    case DESCRIPTION: {
                        updateSet += "description='" + film.getDescription() + "' ";
                    }
                    break;
                    case RATING: {
                        updateSet += "rating=" + film.getRating();
                    }
                    break;
                }
            }
            updateSet += "WHERE ID=" + film.getID();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSet);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
