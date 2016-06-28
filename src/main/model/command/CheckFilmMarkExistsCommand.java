package main.model.command;

import main.model.dao.FilmDAO;
import main.model.dao.MarkDAO;
import main.model.entity.Film;
import main.model.entity.Mark;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to check if film mark exists.
 */

public class CheckFilmMarkExistsCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String userLogin = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");
        String filmName = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        MarkDAO markDAO = new MarkDAO();
        Mark mark = markDAO.getEntity(userLogin, film);
        if (mark != null) {
            try {
                response.getWriter().print(mark.getMark());
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
