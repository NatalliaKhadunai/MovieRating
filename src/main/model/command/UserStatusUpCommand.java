package main.model.command;

import main.controller.Page;
import main.model.exception.UserNotFoundException;
import main.model.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to increase user status.
 */

public class UserStatusUpCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String userLogin = new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
        if (userLogin == null || userLogin.equals("")) throw new UserNotFoundException("User not found!");
        UserManager userManager = new UserManager();
        userManager.userStatusUp(userLogin);
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=userList");
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}