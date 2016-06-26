package main.model.command;

import main.model.dao.MarkDAO;
import main.model.dao.TVSeriesDAO;
import main.model.dao.UserDAO;
import main.model.entity.Film;
import main.model.entity.TVSeries;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Command to put tv series mark.
 */

public class PutTVSeriesMarkCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        int mark = Integer.parseInt(request.getParameter("mark"));
        String userLogin = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");
        String tvseriesName = new String(request.getParameter("tvseriesName").getBytes("ISO-8859-1"), "UTF-8");
        TVSeries tvSeries = getTVSeries(tvseriesName);
        MarkDAO markDAO = new MarkDAO();
        if (markDAO.getEntity(userLogin, tvSeries) == null) {
            markDAO.addEntity(userLogin, tvSeries, mark);
            HttpSession session = request.getSession(false);
            User user = (User)session.getAttribute("loggedUser");
            UserDAO userDAO = new UserDAO();
            userDAO.addPoint(user, countPoint(markDAO, tvSeries, mark));
            userDAO.closeConnection();
            markDAO.closeConnection();
            updateFilmRating(tvSeries);
        }
        markDAO.closeConnection();
        return page;
    }

    /**
     * Return TVSeries entity.
     * @param tvseriesName value, that identifies TVSeries entity.
     * @return TVSeries entity.
     */
    private TVSeries getTVSeries(String tvseriesName) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        return tvSeries;
    }

    /**
     * Update tv series rating.
     * @param tvSeries entity to update.
     */
    private void updateFilmRating(TVSeries tvSeries) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        tvSeriesDAO.updateRating(tvSeries);
        tvSeriesDAO.closeConnection();
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
     * @param markDAO DAO for mark table.
     * @param tvSeries entity, which was estimated.
     * @param mark value.
     * @return counted points.
     */
    private double countPoint(MarkDAO markDAO, TVSeries tvSeries, int mark) {
        double resultPoint = 0;
        int numOfMarks = markDAO.numberOfMarks(tvSeries);
        int numOfUsers = getNumberOfUsers();
        if (numOfMarks==0) numOfMarks = 1;
        double differenceMark = numOfUsers / numOfMarks;
        double avgMark = markDAO.averageMark(tvSeries);
        if (mark >= avgMark - differenceMark && mark <= avgMark + differenceMark) resultPoint = 0.1;
        else resultPoint = -0.1;
        return resultPoint;
    }
}