package main.model.dao;

import main.model.manager.QueryManager;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO extends AbstractDAO {

    public void addEntity(String fileName) {
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

    public List<String> formHomeSliderImagesList(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        List<String> pictureFileNames = new ArrayList<String>();
        for (File file : files) {
          pictureFileNames.add(file.getName());
        }
        return pictureFileNames;
    }

    public void removeHomeSliderImage(String path) {
        File file = new File(path);
        file.delete();
    }
}
