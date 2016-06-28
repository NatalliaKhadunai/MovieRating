package main.model.command;

import main.controller.Page;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command to form tv series list.
 */

public class TVSeriesListCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        List<TVSeries> tvSeriesList = tvSeriesDAO.getAllEntities();
        tvSeriesDAO.closeConnection();
        request.setAttribute("tvseriesList", tvSeriesList);
        try {
            request.getRequestDispatcher(Page.PROCESS_TVSERIES_PAGE.getPagePath()).forward(request, response);
        }
        catch (IOException | ServletException e) {
            logger.error(e);
        }
    }
}