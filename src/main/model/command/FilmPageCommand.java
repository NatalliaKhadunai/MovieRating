package main.model.command;

import main.controller.Page;
import main.controller.RequestType;
import main.model.dao.CommentDAO;
import main.model.dao.FilmDAO;
import main.model.dao.MarkDAO;
import main.model.entity.Comment;
import main.model.entity.Film;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Command to form film page.
 */

public class FilmPageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String title = new String(request.getParameter("filmTitle").getBytes("ISO-8859-1"), "UTF-8");
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getEntity(title);
        filmDAO.closeConnection();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("loggedUser");
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> commentList = commentDAO.getAllEntities(film);
        commentDAO.closeConnection();
        request.setAttribute("film", film);
        request.setAttribute("commentList", commentList);
        page = Page.FILM_PAGE.getPagePath();
        return page;
    }
}