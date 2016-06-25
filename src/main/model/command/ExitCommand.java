package main.model.command;

import main.controller.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * User exit command.
 */

public class ExitCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        HttpSession session = request.getSession(false);
        session.setAttribute("isLoggedIn", false);
        session.removeAttribute("loggedUser");
        page = Page.SERVICE_SERVLET.getPagePath();
        return page;
    }
}