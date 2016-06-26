package main.model.command;

import main.controller.Page;
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
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

/**
 * Command to add film entity to database.
 */

public class AddFilmCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                Film film = fillInfo(multiparts);
                savePoster(multiparts, film);
                ImageDAO imageDAO = new ImageDAO();
                imageDAO.addEntity(film.getPosterFileName());
                imageDAO.closeConnection();
                FilmDAO filmDAO = new FilmDAO();
                filmDAO.addEntity(film);
                filmDAO.closeConnection();
                page = Page.LOGGED_USER_PAGE.getPagePath();
            } catch (FileUploadException e) {
                logger.error(e);
            }
        }
        return page;
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

    /**
     * Save film poster.
     * @param multiparts incoming data.
     * @param film entity.
     */
    private void savePoster(List<FileItem> multiparts, Film film) {
        String directory = PathsManager.getProperty("posters");
        for(FileItem item : multiparts){
            if(!item.isFormField()) {
                try {
                    String name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                    String[] nameParts = name.split("\\.");
                    String format = nameParts[nameParts.length - 1];
                    String filmName = film.getName().toLowerCase().replaceAll(" ", "-");
                    film.setPosterFileName(filmName.hashCode() + name.hashCode() + "." + format);
                    item.write(new File(directory + File.separator + film.getPosterFileName()));
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}