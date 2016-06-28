package main.model.command;

import main.controller.Page;
import main.model.dao.FilmDAO;
import main.model.entity.Film;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to form film edit page.
 */

public class FilmEditPageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String title = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(title);
        filmDAO.closeConnection();
        request.setAttribute("film", film);
        try {
            request.getRequestDispatcher(Page.EDIT_FILM_PAGE.getPagePath()).forward(request, response);
        }
        catch (IOException | ServletException e) {
            logger.error(e);
        }
    }
}
