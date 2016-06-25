package main.model.command;

import main.controller.Page;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command to form tv series list.
 */

public class TVSeriesListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        List<TVSeries> tvSeriesList = tvSeriesDAO.getAllEntities();
        tvSeriesDAO.closeConnection();
        request.setAttribute("tvseriesList", tvSeriesList);
        page = Page.PROCESS_TVSERIES_PAGE.getPagePath();
        return page;
    }
}