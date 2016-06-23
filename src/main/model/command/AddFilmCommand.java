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

public class AddFilmCommand implements ActionCommand {
    private final Logger logger = Logger.getLogger("CommandLogger");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                String filmName = null;
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                Film film = fillInfo(multiparts);
                ImageDAO imageDAO = new ImageDAO();
                imageDAO.addEntity(film.getPosterFileName());
                imageDAO.closeConnection();
                FilmDAO filmDAO = new FilmDAO();
                filmDAO.addEntity(film);
                filmDAO.closeConnection();
                page = Page.USER_PAGE.getPagePath();
            } catch (FileUploadException e) {
                logger.error(e);
            }
        }
        return page;
    }

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

    private void savePoster(List<FileItem> multiparts, Film film) {
        String directory = PathsManager.getProperty("posters");
        for(FileItem item : multiparts){
            if(!item.isFormField()) {
                try {
                    String name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                    String[] nameParts = name.split("\\.");
                    String format = nameParts[nameParts.length - 1];
                    String filmName = film.getName().toLowerCase().replaceAll(" ", "-");
                    film.setPosterFileName(filmName + "." + format);
                    item.write(new File(directory + File.separator + filmName + "." + format));
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}