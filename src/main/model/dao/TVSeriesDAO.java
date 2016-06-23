package main.model.dao;

import main.model.manager.QueryManager;
import main.model.entity.TVSeries;
import main.model.entity.VideoProduct;
import main.TVSeriesField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TVSeriesDAO extends AbstractDAO {
    public List<TVSeries> getTopTVSeries() {
        List<TVSeries> tvSeriesList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getTopTVSeries"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TVSeries tvSeries = new TVSeries();
                tvSeries.setID(resultSet.getInt("ID"));
                tvSeries.setName(resultSet.getString("Name"));
                tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
                tvSeries.setEndYear(resultSet.getInt("EndYear"));
                tvSeries.setDescription(resultSet.getString("Description"));
                tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
                tvSeries.setRating(resultSet.getFloat("Rating"));
                tvSeriesList.add(tvSeries);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return tvSeriesList;
        }
    }

    public List<VideoProduct> getTopCommented() {
        List<VideoProduct> tvSeriesList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getTopCommented"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TVSeries tvSeries = new TVSeries();
                tvSeries.setID(resultSet.getInt("ID"));
                tvSeries.setName(resultSet.getString("Name"));
                tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
                tvSeries.setEndYear(resultSet.getInt("EndYear"));
                tvSeries.setDescription(resultSet.getString("Description"));
                tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
                tvSeries.setRating(resultSet.getFloat("Rating"));
                tvSeries.setPosterFileName(resultSet.getString("FileName"));
                tvSeries.setNumOfComments(resultSet.getInt("NumOfComments"));
                tvSeriesList.add(tvSeries);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return tvSeriesList;
        }
    }

    public TVSeries getEntity(String title) {
        TVSeries tvSeries = new TVSeries();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getEntityByName"));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tvSeries.setID(resultSet.getInt("ID"));
            tvSeries.setName(resultSet.getString("Name"));
            tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
            tvSeries.setEndYear(resultSet.getInt("EndYear"));
            tvSeries.setDescription(resultSet.getString("Description"));
            tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
            tvSeries.setRating(resultSet.getFloat("Rating"));
            tvSeries.setPosterFileName(resultSet.getString("FileName"));
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return tvSeries;
        }
    }

    public TVSeries getEntity(int ID) {
        TVSeries tvSeries = new TVSeries();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getEntityByID"));
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tvSeries.setID(resultSet.getInt("ID"));
            tvSeries.setName(resultSet.getString("Name"));
            tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
            tvSeries.setEndYear(resultSet.getInt("EndYear"));
            tvSeries.setDescription(resultSet.getString("Description"));
            tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
            tvSeries.setRating(resultSet.getFloat("Rating"));
            tvSeries.setPosterFileName(resultSet.getString("FileName"));
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return tvSeries;
        }
    }

    public void addEntity(TVSeries tvSeries) {
        try {
            PreparedStatement selectImageStatement = connection.prepareStatement(QueryManager.getProperty("selectImage"));
            selectImageStatement.setString(1, tvSeries.getPosterFileName());
            ResultSet resultSet = selectImageStatement.executeQuery();
            resultSet.next();
            int imageId = resultSet.getInt("ID");
            resultSet.close();
            selectImageStatement.close();
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.addEntity"));
            preparedStatement.setInt(1, imageId);
            preparedStatement.setString(2, tvSeries.getName());
            preparedStatement.setInt(3, tvSeries.getReleaseYear());
            preparedStatement.setInt(4, tvSeries.getEndYear());
            preparedStatement.setInt(5, tvSeries.getNumOfSeasons());
            preparedStatement.setString(6, tvSeries.getDescription());
            preparedStatement.setFloat(7, tvSeries.getRating());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public List<TVSeries> getAllEntities(String title) {
        List<TVSeries> tvSeriesList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getAllEntitiesByTitle"));
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TVSeries tvSeries = new TVSeries();
                tvSeries.setID(resultSet.getInt("ID"));
                tvSeries.setName(resultSet.getString("Name"));
                tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
                tvSeries.setEndYear(resultSet.getInt("EndYear"));
                tvSeries.setDescription(resultSet.getString("Description"));
                tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
                tvSeries.setRating(resultSet.getFloat("Rating"));
                tvSeries.setPosterFileName(resultSet.getString("FileName"));
                tvSeriesList.add(tvSeries);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return tvSeriesList;
        }
    }

    public List<TVSeries> getAllEntities() {
        List<TVSeries> tvSeriesList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getAllEntities"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TVSeries tvSeries = new TVSeries();
                tvSeries.setID(resultSet.getInt("ID"));
                tvSeries.setName(resultSet.getString("Name"));
                tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
                tvSeries.setEndYear(resultSet.getInt("EndYear"));
                tvSeries.setDescription(resultSet.getString("Description"));
                tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
                tvSeries.setRating(resultSet.getFloat("Rating"));
                tvSeries.setPosterFileName(resultSet.getString("FileName"));
                tvSeriesList.add(tvSeries);
            }
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        finally {
            return tvSeriesList;
        }
    }

    public void removeEntity(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.removeEntity"));
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public void updateEntity(TVSeries tvSeries, List<TVSeriesField> listToUpdate) {
        try {
            String updateSet = "UPDATE tvseries SET ";
            for (TVSeriesField field : listToUpdate) {
                switch (field) {
                    case NAME: {
                        updateSet += "name='" + tvSeries.getName() + "' ";
                    }
                    break;
                    case RELEASE_YEAR: {
                        updateSet += "releaseYear=" + tvSeries.getReleaseYear();
                    }
                    break;
                    case END_YEAR: {
                        updateSet += "endYear=" + tvSeries.getEndYear();
                    }
                    break;
                    case NUMBER_OF_SEASONS: {
                        updateSet += "numOfSeasons=" + tvSeries.getNumOfSeasons();
                    }
                    break;
                    case DESCRIPTION: {
                        updateSet += "description='" + tvSeries.getDescription() + "' ";
                    }
                    break;
                    case RATING: {
                        updateSet += "rating=" + tvSeries.getRating();
                    }
                    break;
                }
            }
            updateSet += "WHERE ID=" + tvSeries.getID();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSet);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
