package main.model.command;

import main.model.dao.FilmDAO;
import main.model.dao.MarkDAO;
import main.model.dao.UserDAO;
import main.model.entity.Film;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Command to put film mark.
 */

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
            markDAO.closeConnection();
            HttpSession session = request.getSession(false);
            User user = (User)session.getAttribute("loggedUser");
            UserDAO userDAO = new UserDAO();
            userDAO.addPoint(user, countPoint(film, mark));
            userDAO.closeConnection();
            markDAO.closeConnection();
            updateFilmRating(film);
        }
        return page;
    }

    /**
     * Return Film entity.
     * @param filmName value, that identifies Film entity.
     * @return Film entity.
     */
    private Film getFilm(String filmName) {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        return film;
    }

    /**
     * Update film rating.
     * @param film entity to update.
     */
    private void updateFilmRating(Film film) {
        FilmDAO filmDAO = new FilmDAO();
        filmDAO.updateRating(film);
        filmDAO.closeConnection();
    }

    /**
     * Return number of users in the system.
     * @return number of users in the system.
     */
    private int getNumberOfUsers() {
        int result = 0;
        UserDAO userDAO = new UserDAO();
        result = userDAO.getNumOfUsers();
        userDAO.closeConnection();
        return result;
    }

    /**
     * Define how many points should be user status coefficient increased or decreased.
     * @param film entity, which was estimated.
     * @param mark value.
     * @return counted points.
     */
    private double countPoint(Film film, int mark) {
        double resultPoint = 0;
        MarkDAO markDAO = new MarkDAO();
        int numOfMarks = markDAO.numberOfMarks(film);
        int numOfUsers = getNumberOfUsers();
        if (numOfMarks==0) numOfMarks = 1;
        double differenceMark = numOfUsers / numOfMarks;
        double avgMark = film.getRating();
        if (mark >= avgMark - differenceMark && mark <= avgMark + differenceMark) resultPoint = 0.1;
        else resultPoint = -0.1;
        markDAO.closeConnection();
        return resultPoint;
    }
}