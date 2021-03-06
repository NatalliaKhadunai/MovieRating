package main.model.command;

import main.controller.Page;
import main.model.dao.FilmDAO;
import main.model.dao.ImageDAO;
import main.model.entity.Film;
import main.model.manager.ImageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to remove film entity from database.
 */

public class FilmRemoveCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String title = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(title);
        filmDAO.removeEntity(title);
        filmDAO.closeConnection();
        ImageDAO imageDAO = new ImageDAO();
        imageDAO.removeEntity(film.getPosterFileName());
        imageDAO.closeConnection();
        ImageManager.removePoster(film);
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=filmList");
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}