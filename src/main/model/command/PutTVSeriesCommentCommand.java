package main.model.command;

import main.model.dao.CommentDAO;
import main.model.dao.TVSeriesDAO;
import main.model.dao.UserDAO;
import main.model.entity.TVSeries;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Command to put tv series comment.
 */

public class PutTVSeriesCommentCommand implements ActionCommand {
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
            Locale locale = defineLocale(request);
            String resultComment = recentComments(tvseriesName, dateLastModified, locale);

            response.setContentType("application/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resultComment);
        }
        catch (IOException e) {
            logger.error(e);
        }
        return page;
    }

    /**
     * Define timestamp from milliseconds.
     * @param timeMillis value, represents time in milliseconds.
     * @return computed TimeStamp.
     */
    private Timestamp defineDateTime(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String dateStr = formatter.format(calendar.getTime());
        Timestamp date = Timestamp.valueOf(dateStr);
        return date;
    }

    /**
     * Add comment entity.
     * @param tvseriesName represents tv series, on which comment was made.
     * @param login represents user, who made comment.
     * @param content represents content of the comment.
     * @param date represents date, when comment was made.
     */
    private void addComment(String tvseriesName, String login, String content, Timestamp date) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        commentDAO.addEntity(tvSeries, login, content, date);
        commentDAO.closeConnection();
    }

    /**
     * Return comments made on tv series, that were left after specified time.
     * @param tvseriesName represents tv series, on which comment was made.
     * @param dateLastModified time border.
     * @return String value in XML-format, that contains info about found comments.
     */
    private String recentComments(String tvseriesName, Timestamp dateLastModified, Locale locale) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        String resultComment = commentDAO.getLatestComments(tvSeries, dateLastModified, locale);
        commentDAO.closeConnection();
        return resultComment;
    }

    private Locale defineLocale(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String localeStr = (String)session.getAttribute("locale");
        Locale locale = new Locale(localeStr.toLowerCase(), localeStr.toUpperCase());
        return locale;
    }
}