package main.model.command;

import main.model.dao.FilmDAO;
import main.model.dao.MarkDAO;
import main.model.dao.UserDAO;
import main.model.entity.Film;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class PutFilmMarkCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        int mark = Integer.parseInt(request.getParameter("mark"));
        String userLogin = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");
        String filmName = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        Film film = getFilm(filmName);
        MarkDAO markDAO = new MarkDAO();
        if (markDAO.getEntity(userLogin, film) == null) {
            markDAO.addEntity(userLogin, film, mark);
            int numOfComments = markDAO.numberOfComments(film);
            float differenceMark = 10 / numOfComments;
            float avgMark = markDAO.averageMark(film);
            UserDAO userDAO = new UserDAO();
            if (mark >= avgMark - differenceMark || mark <= avgMark + differenceMark) userDAO.addPoint(userLogin, 0.1);
            else userDAO.addPoint(userLogin, -0.1);
            userDAO.closeConnection();
        }
        markDAO.closeConnection();
        return page;
    }

    private Film getFilm(String filmName) {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        return film;
    }
}