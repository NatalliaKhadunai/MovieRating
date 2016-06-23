package main.model.command;

import main.controller.Page;
import main.model.manager.PathsManager;
import main.model.dao.ImageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class RemoveHomeSliderImageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String filename = new String(request.getParameter("fileName").getBytes("ISO-8859-1"), "UTF-8");
        String directory = PathsManager.getProperty("frames");
        ImageDAO imageDAO = new ImageDAO();
        imageDAO.removeHomeSliderImage(directory + filename);
        imageDAO.closeConnection();
        page = Page.SERVICE_SERVLET.getPagePath() + "?requestType=homeSliderImagesPage";
        return page;
    }
}