package main.model.command;

import main.controller.Page;
import main.model.dao.ImageDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Command to remove tv series from database.
 */

public class TVSeriesRemoveCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String title = new String(request.getParameter("tvseriesName").getBytes("ISO-8859-1"), "UTF-8");
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(title);
        tvSeriesDAO.removeEntity(title);
        tvSeriesDAO.closeConnection();
        ImageDAO imageDAO = new ImageDAO();
        imageDAO.removeEntity(tvSeries.getPosterFileName());
        imageDAO.closeConnection();
        page = Page.SERVICE_SERVLET + "?requestType=tvseriesList";
        return page;
    }
}