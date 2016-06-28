package main.model.command;

import main.controller.Page;
import main.model.exception.ImageFormatException;
import main.model.exception.VideoProductAlreadyExistsException;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.FilmField;
import main.model.dao.FilmDAO;
import main.model.dao.ImageDAO;
import main.model.entity.Film;
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
import java.sql.Date;
import java.util.List;

/**
 * Command to add film entity to database.
 */

public class AddFilmCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = null;
            try {
                multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            } catch (FileUploadException e) {
                logger.error(e);
            }
            Film film = fillInfo(multiparts);
            FilmDAO filmDAO = new FilmDAO();
            Film filmToCompare = filmDAO.getEntity(film.getName());
            if (filmToCompare == null || !filmToCompare.equals(film)) {
                ImageManager.savePoster(multiparts, film);
                ImageDAO imageDAO = new ImageDAO();
                imageDAO.addEntity(film.getPosterFileName());
                imageDAO.closeConnection();
                filmDAO.addEntity(film);
                filmDAO.closeConnection();
            }
            else throw new VideoProductAlreadyExistsException("Such film already exists!");
            try {
                response.sendRedirect(Page.LOGGED_USER_PAGE.getPagePath());
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Fill film object with received data.
     * @param multiparts incoming data.
     * @return incoming data.
     */
    private Film fillInfo(List<FileItem> multiparts) {
        Film film = new Film();
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    FilmField fieldName = FilmField.fromValue(item.getFieldName());
                    String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                    if (fieldName != null) {
                        switch (fieldName) {
                            case NAME: {
                                film.setName(value);
                            }
                            break;
                            case RELEASE_DATE: {
                                film.setReleaseDate(Date.valueOf(value));
                            }
                            break;
                            case DESCRIPTION: {
                                film.setDescription(value);
                            }
                            break;
                            case RATING: {
                                if (value.length() != 0) film.setRating(Float.valueOf(value));
                                else film.setRating(0);
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
        return film;
    }
}