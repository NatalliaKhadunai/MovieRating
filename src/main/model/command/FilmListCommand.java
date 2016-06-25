package main.model.command;

import main.controller.Page;
import main.model.dao.FilmDAO;
import main.model.entity.Film;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command to form process film page.
 */

public class FilmListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        FilmDAO filmDAO = new FilmDAO();
        List<Film> filmList = filmDAO.getAllEntities();
        filmDAO.closeConnection();
        request.setAttribute("filmList", filmList);
        page = Page.PROCESS_FILMS_PAGE.getPagePath();
        return page;
    }
}