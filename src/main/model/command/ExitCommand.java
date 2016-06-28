package main.model.command;

import main.controller.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User exit command.
 */

public class ExitCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.setAttribute("isLoggedIn", false);
        session.removeAttribute("loggedUser");
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath());
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}