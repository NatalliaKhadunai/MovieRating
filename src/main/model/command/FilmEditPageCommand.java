package main.model.command;

import main.controller.Page;
import main.model.dao.FilmDAO;
import main.model.entity.Film;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Command to form film edit page.
 */

public class FilmEditPageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String title = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(title);
        filmDAO.closeConnection();
        request.setAttribute("film", film);
        page = Page.EDIT_FILM_PAGE.getPagePath();
        return page;
    }
}
