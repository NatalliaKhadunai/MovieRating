package main.model.command;

import main.controller.Page;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.model.dao.ImageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command to form home slider image management page.
 */

public class HomeSliderImagePageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<String> images = ImageManager.formHomeSliderImagesList();
        request.setAttribute("homeSliderImages", images);
        try {
            request.getRequestDispatcher(Page.MANAGE_HOME_SLIDER_IMAGES_PAGE.getPagePath()).forward(request, response);
        }
        catch (IOException | ServletException e) {
            logger.error(e);
        }
    }
}