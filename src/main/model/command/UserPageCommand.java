package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command to form user page.
 */

public class UserPageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getEntity(request.getParameter("userLogin"));
        userDAO.closeConnection();
        HttpSession session = request.getSession(false);
        User loggedUser = (User)session.getAttribute("loggedUser");
        if (loggedUser != null && loggedUser.getName()!= null && loggedUser.getName().equals(user.getName())) {
            try {
                response.sendRedirect(Page.LOGGED_USER_PAGE.getPagePath());
            }
            catch (IOException e) {
                logger.error(e);
            }
        }
        else {
            request.setAttribute("user", user);
            try {
                request.getRequestDispatcher(Page.USER_PAGE.getPagePath()).forward(request, response);
            }
            catch (IOException | ServletException e) {
                logger.error(e);
            }
        }
    }
}