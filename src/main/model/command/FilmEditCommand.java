package main.model.command;

import main.controller.Page;
import main.model.exception.ImageFormatException;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.FilmField;
import main.model.dao.FilmDAO;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Command to update film entity.
 */

public class FilmEditCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        int filmID = Integer.valueOf(request.getParameter("filmID"));
        if(ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = null;
            try {
                multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            } catch (FileUploadException e) {
                logger.error(e);
            }
            FilmDAO filmDAO = new FilmDAO();
            Film film = filmDAO.getEntity(filmID);
            List<FilmField> updatedFields = getUpdatedFields(multiparts);
            updateFields(multiparts, updatedFields, film);
            ImageManager.savePoster(multiparts, film);
            filmDAO.updateEntity(film, updatedFields);
            filmDAO.closeConnection();
            try {
                response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=filmList");
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Define fields to update.
     * @param multiparts incoming data.
     * @return List with fields to update.
     */
    private List<FilmField> getUpdatedFields(List<FileItem> multiparts) {
        List<FilmField> updatedFields = new ArrayList<>();
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("field")) {
                        FilmField value = FilmField.fromValue(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                        updatedFields.add(value);
                    }
                }
                catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
            }
        }
        return updatedFields;
    }

    /**
     * Update user entity fields.
     * @param multiparts incoming data.
     * @param updatedFields list of fields to update.
     * @param film entity to update.
     */
    private void updateFields(List<FileItem> multiparts, List<FilmField> updatedFields, Film film) {
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    FilmField fieldName = FilmField.fromValue(item.getFieldName());
                    String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                    if (fieldName != null && updatedFields.contains(fieldName) && value.length() != 0) {
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
                                film.setRating(Float.valueOf(value));
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
    }
}
