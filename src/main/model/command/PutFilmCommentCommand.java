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

public class PutFilmCommentCommand implements ActionCommand {
    private final Logger logger = Logger.getLogger("CommandLogger");
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
        addPoint(login);

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

    private Timestamp defineDateTime(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String dateStr = formatter.format(calendar.getTime());
        Timestamp date = Timestamp.valueOf(dateStr);
        return date;
    }

    private void addComment(String filmName, String login, String content, Timestamp date) {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        commentDAO.addEntity(film, login, content, date);
        commentDAO.closeConnection();
    }

    private String recentComments(String filmName, Timestamp dateLastModified) {
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(filmName);
        filmDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        String resultComment = commentDAO.getLatestComments(film, dateLastModified);
        commentDAO.closeConnection();
        return resultComment;
    }

    private void addPoint(String login) {
        UserDAO userDAO = new UserDAO();
        userDAO.addPoint(login, 0.1);
        userDAO.closeConnection();
    }
}
