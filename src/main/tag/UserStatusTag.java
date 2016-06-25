package main.tag;

import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class UserStatusTag extends TagSupport {
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    private final Logger logger = Logger.getLogger("TagLogger");
    private final static String GOLD_STATUS_ICON_CLASS = "glyphicon glyphicon-king";
    private final static String GOLD_STATUS_COLOR = "color: gold";
    private final static String SILVER_STATUS_ICON_CLASS = "glyphicon glyphicon-knight";
    private final static String SILVER_STATUS_COLOR = "color: silver";
    private final static String BRONZE_STATUS_ICON_CLASS = "glyphicon glyphicon-pawn";
    private final static String BRONZE_STATUS_COLOR = "color: #CD7F32";
    private final static String BAN_STATUS_ICON_CLASS = "glyphicon glyphicon-minus-sign";
    private final static String BAN_STATUS_COLOR = "color: red";

    /**
     * Form top-rated tv series list and write it to the output channel.
     * @return fail or success.
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String resultStr = "<span class=\"";
            switch (status) {
                case "GOLD": {
                    resultStr += GOLD_STATUS_ICON_CLASS + "\" style=\"" + GOLD_STATUS_COLOR + "\"></span>" + status;
                }
                break;
                case "SILVER": {
                    resultStr += SILVER_STATUS_ICON_CLASS + "\" style=\"" + SILVER_STATUS_COLOR + "\"></span>" + status;
                }
                break;
                case "BRONZE": {
                    resultStr += BRONZE_STATUS_ICON_CLASS + "\" style=\"" + BRONZE_STATUS_COLOR + "\"></span>" + status;
                }
                break;
                case "BAN": {
                    resultStr += BAN_STATUS_ICON_CLASS + "\" style=\"" + BAN_STATUS_COLOR + "\"></span>" + status;
                }
                break;
                default: {
                    resultStr = status;
                }
                break;
            }
            out.print(resultStr);
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
