package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command to form user list.
 */

public class UserListCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllEntities();
        userDAO.closeConnection();
        request.setAttribute("userList", users);
        try {
            request.getRequestDispatcher(Page.PROCESS_USERS.getPagePath()).forward(request, response);
        }
        catch (IOException | ServletException e) {
            logger.error(e);
        }
    }
}