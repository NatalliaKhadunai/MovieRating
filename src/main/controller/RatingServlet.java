package main.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

import main.model.command.ActionCommand;

/**
 * Class, that represents main controller of the system.
 */

@WebServlet(name = "RatingServlet")
public class RatingServlet extends HttpServlet {

    /**
     * Method that handles "post" requests.
     * @param request incoming request.
     * @param response outcoming response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method that handles "get" requests.
     * @param request incoming request.
     * @param response outcoming response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method defines request, sends it on execution and redirects to given page.
     * @param request incoming request.
     * @param response outcoming response.
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        String page = command.execute(request, response);
        if (page != null) {
            if (page.equals(Page.SERVICE_SERVLET.getPagePath())) response.sendRedirect(page);
            else getServletContext().getRequestDispatcher(page).forward(request, response);
        }
    }
}
