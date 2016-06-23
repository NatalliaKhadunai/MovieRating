package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class RegistrationCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String login = new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        if (user != null) {
            request.setAttribute("loginExists", true);
            page = Page.SIGN_UP_PAGE.getPagePath();
        }
        else {
            String email = new String(request.getParameter("email").getBytes("ISO-8859-1"), "UTF-8");
            int password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "UTF-8").hashCode();
            user = createUser(login, email, password);
            HttpSession session = request.getSession(false);
            session.setAttribute("isLoggedIn", true);
            request.setAttribute("user", user);
            page = Page.USER_PAGE.getPagePath();
        }
        return page;
    }

    private User createUser(String login, String email, int password) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        UserDAO userDAO = new UserDAO();
        userDAO.addEntity(user);
        userDAO.closeConnection();
        return user;
    }
}
