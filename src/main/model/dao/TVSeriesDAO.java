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

/**
 * Class {@code TVSeriesDAO} is a class, with the help of which data about tvSeries is extracted from database.
 * {@see main.model.entity.TVSeries}
 */

public class TVSeriesDAO extends AbstractDAO {
    /**
     * Return top-rated tvSeries.
     * @return List with top-rated TVSeries entities.
     */
    public List<TVSeries> getTopRatedTVSeries() {
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
        return tvSeriesList;
    }

    /**
     * Return top-commented tvSeries.
     * @return List with top-commented TVSeries entities.
     */
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
        return tvSeriesList;
    }

    /**
     * Search tvSeries by title.
     * @param title value.
     * @return TVSeries entity.
     */
    public TVSeries getEntity(String title) {
        if (title == null || title.equals("")) return null;
        TVSeries tvSeries = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getEntityByName"));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            tvSeries = getSingleEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return tvSeries;
    }

    /**
     * Search tvSeries by ID.
     * @param ID value.
     * @return TVSeries entity.
     */
    public TVSeries getEntity(int ID) {
        if (ID <= 0) return null;
        TVSeries tvSeries = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getEntityByID"));
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            tvSeries = getSingleEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return tvSeries;
    }

    /**
     * Return single entity from database result set.
     * @param resultSet incoming data set.
     * @return formed entity.
     */
    private TVSeries getSingleEntity(ResultSet resultSet) {
        TVSeries tvSeries = null;
        try {
            if (resultSet.next()) {
                tvSeries = new TVSeries();
                tvSeries.setID(resultSet.getInt("ID"));
                tvSeries.setName(resultSet.getString("Name"));
                tvSeries.setReleaseYear(resultSet.getInt("ReleaseYear"));
                tvSeries.setEndYear(resultSet.getInt("EndYear"));
                tvSeries.setDescription(resultSet.getString("Description"));
                tvSeries.setNumOfSeasons(resultSet.getInt("NumOfSeasons"));
                tvSeries.setRating(resultSet.getFloat("Rating"));
                tvSeries.setPosterFileName(resultSet.getString("FileName"));
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return tvSeries;
    }

    private List<TVSeries> getListEntity(ResultSet resultSet) {
        List<TVSeries> tvSeriesList = new ArrayList<>();
        try {
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
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return tvSeriesList;
    }

    /**
     * Add entity.
     * @param tvSeries entity to add.
     */
    public void addEntity(TVSeries tvSeries) {
        if (tvSeries == null) return;
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

    /**
     * Search tvSeries by title.
     * @param title value.
     * @return List of TVSeries entities.
     */
    public List<TVSeries> getAllEntities(String title) {
        List<TVSeries> tvSeriesList = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getAllEntitiesByTitle"));
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            tvSeriesList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return tvSeriesList;
    }

    /**
     * Return all TVSeries entities.
     * @return List with TVSeries entities.
     */
    public List<TVSeries> getAllEntities() {
        List<TVSeries> tvSeriesList = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.getAllEntities"));
            ResultSet resultSet = preparedStatement.executeQuery();
            tvSeriesList = getListEntity(resultSet);
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
        return tvSeriesList;
    }

    /**
     * Remove entity.
     * @param title value of entity to remove.
     */
    public void removeEntity(String title) {
        if (title == null || title.equals("")) return;
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

    /**
     * Update entity.
     * @param tvSeries entity to update.
     * @param listToUpdate fields to update.
     */
    public void updateEntity(TVSeries tvSeries, List<TVSeriesField> listToUpdate) {
        if (tvSeries == null || listToUpdate.size() == 0) return;
        try {
            String updateSet = "UPDATE tvseries SET ";
            for (TVSeriesField field : listToUpdate) {
                switch (field) {
                    case NAME: {
                        updateSet += "name='" + tvSeries.getName() + "',";
                    }
                    break;
                    case RELEASE_YEAR: {
                        updateSet += "releaseYear=" + tvSeries.getReleaseYear() + ",";
                    }
                    break;
                    case END_YEAR: {
                        updateSet += "endYear=" + tvSeries.getEndYear() + ",";
                    }
                    break;
                    case NUMBER_OF_SEASONS: {
                        updateSet += "numOfSeasons=" + tvSeries.getNumOfSeasons() + ",";
                    }
                    break;
                    case DESCRIPTION: {
                        updateSet += "description='" + tvSeries.getDescription() + "',";
                    }
                    break;
                    case RATING: {
                        updateSet += "rating=" + tvSeries.getRating() + ",";
                    }
                    break;
                }
            }
            updateSet = updateSet.substring(0, updateSet.length() - 1);
            updateSet += " WHERE ID=" + tvSeries.getID();
            PreparedStatement preparedStatement = connection.prepareStatement(updateSet);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Update tv series rating.
     * @param tvSeries entity to update.
     */
    public void updateRating(TVSeries tvSeries) {
        if (tvSeries == null) return;
        MarkDAO markDAO = new MarkDAO();
        double avgMark = Math.rint(10.0 * (markDAO.averageMark(tvSeries))) / 10.0;
        markDAO.closeConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QueryManager.getProperty("tvseriesDAO.updateRating"));
            preparedStatement.setDouble(1, avgMark);
            preparedStatement.setInt(2, tvSeries.getID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
