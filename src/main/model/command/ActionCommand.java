package main.model.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Interface, which is implemented by every command in the system.
 */

public interface ActionCommand {
    static final Logger logger = Logger.getLogger("CommandLogger");
    /**
     * Command execution.
     * @param request incoming request.
     * @param response outcoming response.
     * @return page to redirect.
     * @throws UnsupportedEncodingException
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;
}
