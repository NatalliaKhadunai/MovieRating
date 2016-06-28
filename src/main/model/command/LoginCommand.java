package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;
import main.model.exception.WrongLoginOrPasswordException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to login user.
 */

public class LoginCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String login = new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
        String password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "UTF-8");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        HttpSession session = request.getSession(true);
        if (user != null && password.hashCode() == user.getPassword()) {
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("loggedUser", user);
        }
        else throw new WrongLoginOrPasswordException("Wrong login or password!");
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath());
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}
