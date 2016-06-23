package main.model.command;

import main.model.dao.MarkDAO;
import main.model.dao.TVSeriesDAO;
import main.model.dao.UserDAO;
import main.model.entity.TVSeries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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
            int numOfComments = markDAO.numberOfComments(tvSeries);
            float differenceMark = 10 / numOfComments;
            float avgMark = markDAO.averageMark(tvSeries);
            UserDAO userDAO = new UserDAO();
            if (mark >= avgMark - differenceMark && mark <= avgMark + differenceMark) userDAO.addPoint(userLogin, 0.1);
            else userDAO.addPoint(userLogin, -0.1);
            userDAO.closeConnection();
        }
        markDAO.closeConnection();
        return page;
    }

    private TVSeries getTVSeries(String tvseriesName) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        return tvSeries;
    }
}