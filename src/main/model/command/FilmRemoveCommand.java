package main.model.command;

import main.controller.Page;
import main.model.dao.FilmDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Command to remove film entity from database.
 */

public class FilmRemoveCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String title = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        FilmDAO filmDAO = new FilmDAO();
        filmDAO.removeEntity(title);
        filmDAO.closeConnection();
        page = Page.SERVICE_SERVLET.getPagePath() + "?requestType=filmList";
        return page;
    }
}