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
     * Form home slider image list.
     * @return List with filenames of images.
     */
    public List<String> formHomeSliderImagesList() {
        File directory = new File(PathsManager.getProperty("frames"));
        File[] files = directory.listFiles();
        List<String> pictureFileNames = new ArrayList<String>();
        for (File file : files) {
          pictureFileNames.add(file.getName());
        }
        return pictureFileNames;
    }

    /**
     * Remove entity.
     * @param path value represents filename of image to remove.
     */
    public void removeHomeSliderImage(String path) {
        if (path == null || path.equals("")) return;
        File file = new File(PathsManager.getProperty("frames") + path);
        file.delete();
    }
}
