package main.model.command;

import main.model.dao.CommentDAO;
import main.model.dao.TVSeriesDAO;
import main.model.dao.UserDAO;
import main.model.entity.TVSeries;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PutTVSeriesCommentCommand implements ActionCommand {
    private final Logger logger = Logger.getLogger("CommandLogger");
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        try {
            String content = new String(request.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");
            String tvseriesName = new String(request.getParameter("tvseriesName").getBytes("ISO-8859-1"), "UTF-8");
            String login = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");

            Timestamp date = defineDateTime(Long.valueOf(request.getParameter("date")));
            addComment(tvseriesName, login, content, date);
            Timestamp dateLastModified = defineDateTime(Long.valueOf(request.getParameter("lastModified")));
            String resultComment = recentComments(tvseriesName, dateLastModified);
            addPoint(login);
            response.setContentType("application/xml");
            response.getWriter().write(resultComment);
        }
        catch (IOException e) {
            logger.error(e);
        }
        return page;
    }

    private Timestamp defineDateTime(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String dateStr = formatter.format(calendar.getTime());
        Timestamp date = Timestamp.valueOf(dateStr);
        return date;
    }

    private void addComment(String tvseriesName, String login, String content, Timestamp date) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        commentDAO.addEntity(tvSeries, login, content, date);
        commentDAO.closeConnection();
    }

    private String recentComments(String tvseriesName, Timestamp dateLastModified) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        String resultComment = commentDAO.getLatestComments(tvSeries, dateLastModified);
        commentDAO.closeConnection();
        return resultComment;
    }

    private void addPoint(String login) {
        UserDAO userDAO = new UserDAO();
        userDAO.addPoint(login, 0.1);
        userDAO.closeConnection();
    }
}