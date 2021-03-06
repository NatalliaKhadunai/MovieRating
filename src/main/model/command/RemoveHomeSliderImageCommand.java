package main.model.command;

import main.controller.Page;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.model.dao.ImageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RemoveHomeSliderImageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = new String(request.getParameter("fileName").getBytes("ISO-8859-1"), "UTF-8");
        ImageManager.removeHomeSliderImage(filename);
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=homeSliderImagesPage");
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
}