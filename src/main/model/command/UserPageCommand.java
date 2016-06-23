package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserPageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(request.getParameter("userLogin"));
        userDAO.closeConnection();
        HttpSession session = request.getSession(false);
        User loggedUser = (User)session.getAttribute("loggedUser");
        if (loggedUser != null && loggedUser.getName().equals(user.getName())) {
            page = Page.LOGGED_USER_PAGE.getPagePath();
        }
        else {
            request.setAttribute("user", user);
            page = Page.USER_PAGE.getPagePath();
        }
        return page;
    }
}