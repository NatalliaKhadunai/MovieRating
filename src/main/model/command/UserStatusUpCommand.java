package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class UserStatusUpCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        UserDAO userDAO = new UserDAO();
        userDAO.userStatusUp(new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8"));
        List<User> users = userDAO.getAllEntities();
        userDAO.closeConnection();
        request.setAttribute("userList", users);
        page = Page.PROCESS_USERS.getPagePath();
        return page;
    }
}