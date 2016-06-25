package main.tag;

import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Class, in which tag is implemented. In this tag list with top-rated tv series is formed.
 */

public class TopRatedTVSeriesTag extends TagSupport {
    private final Logger logger = Logger.getLogger("TagLogger");

    /**
     * Form top-rated tv series list and write it to the output channel.
     * @return fail or success.
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
            List<TVSeries> tvSeriesList = tvSeriesDAO.getTopRatedTVSeries();
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

    /**
     * Return value, which means that page should be compiled further.
     * @return value, which means that page should be compiled further.
     * @throws JspException
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}