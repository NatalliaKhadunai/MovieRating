package main.model.command;

import main.model.dao.MarkDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.Mark;
import main.model.entity.TVSeries;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to check if tv series mark exists.
 */

public class CheckTVSeriesMarkExistsCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String userLogin = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");
        String tvseriesName = new String(request.getParameter("tvseriesName").getBytes("ISO-8859-1"), "UTF-8");
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesName);
        tvSeriesDAO.closeConnection();
        MarkDAO markDAO = new MarkDAO();
        Mark mark = markDAO.getEntity(userLogin, tvSeries);
        if (mark != null) {
            try {
                response.getWriter().print(mark.getMark());
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
        return page;
    }
}