package main.model.command;

import main.controller.Page;
import main.controller.RequestType;
import main.model.dao.CommentDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.Comment;
import main.model.entity.TVSeries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class TVSeriesPageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String title = new String(request.getParameter("tvseriesTitle").getBytes("ISO-8859-1"), "UTF-8");
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(title);
        tvSeriesDAO.closeConnection();
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> commentList = commentDAO.getAllEntities(title, RequestType.fromValue(request.getParameter("requestType")));
        commentDAO.closeConnection();
        request.setAttribute("tvseries", tvSeries);
        request.setAttribute("commentList", commentList);
        page = Page.TVSERIES_PAGE.getPagePath();
        return page;
    }
}