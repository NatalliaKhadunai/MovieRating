package main.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

import main.model.command.ActionCommand;

@WebServlet(name = "RatingServlet")
public class RatingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

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
