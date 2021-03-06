package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.Status;
import main.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to sign up user.
 */

public class RegistrationCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String login = new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(login);
        userDAO.closeConnection();
        if (user != null) {
            request.setAttribute("loginExists", true);
            try {
                request.getServletContext().getRequestDispatcher(Page.SIGN_UP_PAGE.getPagePath()).forward(request, response);
            }
            catch (IOException | ServletException e) {
                logger.error(e);
            }
        }
        else {
            String email = new String(request.getParameter("email").getBytes("ISO-8859-1"), "UTF-8");
            int password = new String(request.getParameter("password").getBytes("ISO-8859-1"), "UTF-8").hashCode();
            user = createUser(login, email, password);
            HttpSession session = request.getSession(false);
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("loggedUser", user);
            try {
                response.sendRedirect(Page.LOGGED_USER_PAGE.getPagePath());
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Create and add user.
     * @param login value, represents login of new user.
     * @param email value, represents email of new user.
     * @param password value, represents password hash-code of new user.
     * @return User entity.
     */
    private User createUser(String login, String email, int password) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setStatusCoefficient(Status.minimalStatus().lowerThreshold);
        user.setStatusName(Status.minimalStatus().name());
        UserDAO userDAO = new UserDAO();
        userDAO.addEntity(user);
        userDAO.closeConnection();
        return user;
    }
}
