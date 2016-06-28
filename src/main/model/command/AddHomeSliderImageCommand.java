package main.model.command;

import main.controller.Page;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Command to add home slider image.
 */

public class AddHomeSliderImageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                ImageManager.saveFrame(multiparts);
                response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=homeSliderImagesPage");
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }


}