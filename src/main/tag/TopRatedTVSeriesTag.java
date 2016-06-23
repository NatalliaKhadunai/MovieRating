package main.tag;

import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class TopRatedTVSeriesTag extends TagSupport {
    private final Logger logger = Logger.getLogger("TagLogger");

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
            List<TVSeries> tvSeriesList = tvSeriesDAO.getTopTVSeries();
            tvSeriesDAO.closeConnection();
            int i = 1;
            for (TVSeries tvSeries : tvSeriesList) {
                out.print("<tr>");
                out.print("<td>" + i + "</td>");
                out.print("<td>" + "<a href=\"/RatingServlet?requestType=tvseriesPage&tvseriesTitle=" + tvSeries.getName() + "\">" + tvSeries.getName() + "</a>" + "</td>");
                out.print("<td>" + tvSeries.getRating() + "</td>");
                out.print("</tr>");
                i++;
            }
        } catch(IOException e) {
            logger.error(e);
        }
        return SKIP_BODY;
    }


    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}