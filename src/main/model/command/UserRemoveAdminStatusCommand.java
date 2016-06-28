package main.model.command;

import main.controller.Page;
import main.model.entity.User;
import main.model.exception.UserNotFoundException;
import main.model.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command to remove administrator status.
 */
public class UserRemoveAdminStatusCommand implements ActionCommand{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String userLogin = new String(request.getParameter("login").getBytes("ISO-8859-1"), "UTF-8");
        if (userLogin == null || userLogin.equals("")) throw new UserNotFoundException("User not found!");
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (!loggedUser.getLogin().equals(userLogin)) {
            UserManager userManager = new UserManager();
            userManager.removeAdminStatus(userLogin);
        }
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=userList");
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
