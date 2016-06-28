package main.model.command;

import main.controller.Page;
import main.controller.RequestType;
import main.model.dao.CommentDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.Comment;
import main.model.entity.TVSeries;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Command to form tv series page.
 */

public class TVSeriesPageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String title = new String(request.getParameter("tvseriesTitle").getBytes("ISO-8859-1"), "UTF-8");
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(title);
        tvSeriesDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> commentList = commentDAO.getAllEntities(tvSeries);
        commentDAO.closeConnection();
        request.setAttribute("tvseries", tvSeries);
        request.setAttribute("commentList", commentList);
        try {
            request.getRequestDispatcher(Page.TVSERIES_PAGE.getPagePath()).forward(request, response);
        }
        catch (IOException | ServletException e) {
            logger.error(e);
        }
    }
}