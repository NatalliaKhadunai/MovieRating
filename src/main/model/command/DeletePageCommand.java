package main.model.command;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
import main.controller.Page;
import main.model.dao.UserDAO;
import main.model.entity.User;
import main.model.manager.ImageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Command used to delete users page.
 */
public class DeletePageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session = request.getSession(false);
        User loggedUser = (User) session.getAttribute("loggedUser");
        UserDAO userDAO = new UserDAO();
        userDAO.removeEntity(loggedUser.getLogin());
        userDAO.closeConnection();
        ImageManager.removeProfilePhoto(loggedUser);
        session.removeAttribute("loggedUser");
        session.setAttribute("isLoggedIn", false);
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
