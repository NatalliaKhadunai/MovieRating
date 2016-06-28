package main.model.command;

import main.controller.Page;
import main.model.exception.ImageFormatException;
import main.model.exception.VideoProductAlreadyExistsException;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.model.dao.ImageDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import main.TVSeriesField;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Command to add tv series entity to database.
 */

public class AddTVSeriesCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if(ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = null;
            try {
                multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            } catch (FileUploadException e) {
                logger.error(e);
            }
            TVSeries tvSeries = fillInfo(multiparts);
            TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
            TVSeries tvSeriesToCompare = tvSeriesDAO.getEntity(tvSeries.getName());
            if (tvSeriesToCompare == null || !tvSeriesToCompare.equals(tvSeries)) {
                ImageManager.savePoster(multiparts, tvSeries);
                ImageDAO imageDAO = new ImageDAO();
                imageDAO.addEntity(tvSeries.getPosterFileName());
                imageDAO.closeConnection();
                tvSeriesDAO.addEntity(tvSeries);
                tvSeriesDAO.closeConnection();
            }
            else throw new VideoProductAlreadyExistsException("Such tv series already exists!");
            try {
                response.sendRedirect(Page.LOGGED_USER_PAGE.getPagePath());
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Fill tv series object with received data.
     * @param multiparts incoming data.
     * @return incoming data.
     */
    private TVSeries fillInfo(List<FileItem> multiparts) {
        TVSeries tvSeries = new TVSeries();
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    TVSeriesField fieldName = TVSeriesField.fromValue(item.getFieldName());
                    String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                    if (fieldName != null) {
                        switch (fieldName) {
                            case NAME: {
                                tvSeries.setName(value);
                            }
                            break;
                            case RELEASE_YEAR: {
                                tvSeries.setReleaseYear(Integer.valueOf(value));
                            }
                            break;
                            case END_YEAR: {
                                if (value.length() != 0) tvSeries.setEndYear(Integer.valueOf(value));
                            }
                            break;
                            case NUMBER_OF_SEASONS: {
                                tvSeries.setNumOfSeasons(Integer.valueOf(value));
                            }
                            break;
                            case DESCRIPTION: {
                                tvSeries.setDescription(value);
                            }
                            break;
                            case RATING: {
                                if (value.length() != 0) tvSeries.setRating(Float.valueOf(value));
                                else tvSeries.setRating(0);
                            }
                            break;
                        }
                    }
                }
                catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
            }
        }
        return tvSeries;
    }
}
