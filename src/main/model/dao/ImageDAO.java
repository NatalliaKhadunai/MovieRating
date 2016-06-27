package main.model.dao;

import main.model.manager.PathsManager;
import main.model.manager.QueryManager;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code ImageDAO} is a class, with the help of which data about images is extracted from database.
 */

public class ImageDAO extends AbstractDAO {

    /**
     * Add entity.
     * @param fileName value to add.
     */
    public void addEntity(String fileName) {
        if (fileName == null || fileName.equals("")) return;
        try {
            PreparedStatement statement = connection.prepareStatement(QueryManager.getProperty("imageDAO.addEntity"));
            statement.setString(1, fileName);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Remove entity.
     * @param fileName value to remove.
     */
    public void removeEntity(String fileName) {
        if (fileName == null || fileName.equals("")) return;
        try {
            PreparedStatement statement = connection.prepareStatement(QueryManager.getProperty("imageDAO.removeEntity"));
            statement.setString(1, fileName);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
