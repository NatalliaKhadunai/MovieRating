package main.model.command;

import main.controller.Page;
import main.model.manager.PathsManager;
import main.model.dao.ImageDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command to form home slider image management page.
 */

public class HomeSliderImagePageCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        ImageDAO imageDAO = new ImageDAO();
        List<String> images = imageDAO.formHomeSliderImagesList();
        imageDAO.closeConnection();
        request.setAttribute("homeSliderImages", images);
        page = Page.MANAGE_HOME_SLIDER_IMAGES_PAGE.getPagePath();
        return page;
    }
}