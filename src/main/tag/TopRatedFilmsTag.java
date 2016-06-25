package main.tag;

import main.model.dao.FilmDAO;
import main.model.entity.Film;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class TopRatedFilmsTag extends TagSupport {
    private final Logger logger = Logger.getLogger("TagLogger");

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            FilmDAO filmDAO = new FilmDAO();
            List<Film> filmList = filmDAO.getTopRatedFilms();
            filmDAO.closeConnection();
            int i = 1;
            for (Film film : filmList) {
                out.print("<tr>");
                out.print("<td>" + i + "</td>");
                out.print("<td>" + "<a href=\"/RatingServlet?requestType=filmPage&filmTitle=" + film.getName() + "\">" + film.getName() + "</a>" + "</td>");
                out.print("<td>" + film.getRating() + "</td>");
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
