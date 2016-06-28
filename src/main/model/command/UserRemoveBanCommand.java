package main.model.command;

import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Command to remove user ban.
 */

public class UserRemoveBanCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        UserDAO userDAO = new UserDAO();
        userDAO.userRemoveBan(new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8"));
        userDAO.closeConnection();
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=userList");
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}
