package main.model.command;

import main.model.dao.CommentDAO;
import main.model.dao.FilmDAO;
import main.model.dao.UserDAO;
import main.model.entity.Film;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Command to put film comment.
 */

public class PutFilmCommentCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String content = new String(request.getParameter("content").getBytes("ISO-8859-1"), "UTF-8");
        String filmName = new String(request.getParameter("filmName").getBytes("ISO-8859-1"), "UTF-8");
        String login = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");

        Timestamp date = defineDateTime(Long.valueOf(request.getParameter("date")));
        addComment(filmName, login, content, date);
        Timestamp dateLastModified = defineDateTime(Long.valueOf(request.getParameter("lastModified")));
        String resultComment = recentComments(filmName, dateLastModified);

        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        try {
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
     * @param filmName represents film, on which comment was made.
     * @param login represents user, who made comment.
     * @param content represents content of the comment.
     * @param date represents date, when comment was made.
     */
    private void addComment(String filmName, String login, String content, Timestamp date) {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        commentDAO.addEntity(film, login, content, date);
        commentDAO.closeConnection();
    }

    /**
     * Return comments made on film, that were left after specified time.
     * @param filmName represents film, on which comment was made.
     * @param dateLastModified time border.
     * @return String value in XML-format, that contains info about found comments.
     */
    private String recentComments(String filmName, Timestamp dateLastModified) {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        String resultComment = commentDAO.getLatestComments(film, dateLastModified);
        commentDAO.closeConnection();
        return resultComment;
    }
}
