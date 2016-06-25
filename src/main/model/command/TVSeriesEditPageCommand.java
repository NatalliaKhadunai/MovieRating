package main.model.command;

import main.controller.Page;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Command to form tv series edit page.
 */

public class TVSeriesEditPageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String title = new String(request.getParameter("tvseriesName").getBytes("ISO-8859-1"), "UTF-8");
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(title);
        tvSeriesDAO.closeConnection();
        request.setAttribute("tvseries", tvSeries);
        page = Page.EDIT_TVSERIES_PAGE.getPagePath();
        return page;
    }
}