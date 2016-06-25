package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Command to change user password.
 */

public class ChangePasswordCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        int oldPasswordHash = new String(request.getParameter("oldPassword").getBytes("ISO-8859-1"), "UTF-8").hashCode();
        HttpSession session = request.getSession(false);
        String login = ((User)session.getAttribute("loggedUser")).getLogin();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        if (user.getPassword() == oldPasswordHash) {
            int newPasswordHash = new String(request.getParameter("newPassword").getBytes("ISO-8859-1"), "UTF-8").hashCode();
            userDAO.changeUserPassword(login, newPasswordHash);
            userDAO.closeConnection();
            page = Page.SERVICE_SERVLET.getPagePath();
        }
        else {
            request.setAttribute("incorrectPassword", true);
            userDAO.closeConnection();
            page = Page.CHANGE_PASSWORD_PAGE.getPagePath();
        }
        return page;
    }
}