package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;
import main.model.exception.WrongLoginOrPasswordException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class LoginCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = Page.SERVICE_SERVLET.getPagePath();
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
        return page;
    }
}
