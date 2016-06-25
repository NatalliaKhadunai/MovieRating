package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command to form user list.
 */

public class UserListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllEntities();
        userDAO.closeConnection();
        request.setAttribute("userList", users);
        page = Page.PROCESS_USERS.getPagePath();
        return page;
    }
}