package main.model.command;

import main.controller.Page;
import main.model.manager.PathsManager;
import main.FilmField;
import main.model.dao.FilmDAO;
import main.model.entity.Film;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to update film entity.
 */

public class FilmEditCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        int filmID = Integer.valueOf(request.getParameter("filmID"));
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                FilmDAO filmDAO = new FilmDAO();
                Film film = filmDAO.getEntity(filmID);
                List<FilmField> updatedFields = getUpdatedFields(multiparts);
                updateFields(multiparts, updatedFields, film);
                savePoster(multiparts, film);
                filmDAO.updateEntity(film, updatedFields);
                filmDAO.closeConnection();
                page = Page.SERVICE_SERVLET.getPagePath() + "?requestType=filmList";
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return page;
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

    /**
     * Save poster.
     * @param multiparts incoming data.
     * @param film entity to update.
     */
    private void savePoster(List<FileItem> multiparts, Film film) {
        String directory = PathsManager.getProperty("posters");
        for (FileItem item : multiparts){
            if(!item.isFormField()) {
                try {
                    String name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                    if (name.length() != 0) {
                        File file = new File(directory + film.getPosterFileName());
                        if (file.exists()) file.delete();
                        item.write(new File(directory + film.getPosterFileName()));
                    }
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}
