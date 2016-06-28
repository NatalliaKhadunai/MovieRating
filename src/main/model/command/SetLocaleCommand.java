package main.model.command;

import main.controller.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command to change locale.
 */

public class SetLocaleCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String locale = request.getParameter("lang");
        HttpSession session = request.getSession(false);
        switch (locale) {
            case "EN": {
                session.setAttribute("locale", "EN");
            }
            break;
            case "DE": {
                session.setAttribute("locale", "DE");
            }
            break;
            case "RU": {
                session.setAttribute("locale", "RU");
            }
            break;
        }
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath());
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}