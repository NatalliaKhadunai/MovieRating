package main.model.command;

import main.controller.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLocaleCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
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
        page = Page.SERVICE_SERVLET.getPagePath();
        return page;
    }
}