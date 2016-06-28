package main.model.command;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import main.controller.Page;
import main.model.dao.FilmDAO;
import main.model.entity.Film;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command to form process film page.
 */

public class FilmListCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FilmDAO filmDAO = new FilmDAO();
        List<Film> filmList = filmDAO.getAllEntities();
        filmDAO.closeConnection();
        request.setAttribute("filmList", filmList);
        try {
            request.getRequestDispatcher(Page.PROCESS_FILMS_PAGE.getPagePath()).forward(request, response);
        }
        catch (IOException | ServletException e) {
            logger.error(e);
        }
    }
}