package main.model.command;

import main.controller.Page;
import main.model.manager.PathsManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class AddHomeSliderImageCommand implements ActionCommand {
    private final Logger logger = Logger.getLogger("CommandLogger");
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                saveFrame(multiparts);
                page = Page.SERVICE_SERVLET.getPagePath() + "?requestType=homeSliderImagesPage";
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return page;
    }

    private String getVideoProductName(List<FileItem> multiparts) {
        String videoProductName = null;
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                if (fieldName.equals("videoProductName")) {
                    videoProductName = item.getString();
                }
            }
        }
        return videoProductName;
    }

    private void saveFrame(List<FileItem> multiparts) {
        String directory = PathsManager.getProperty("frames");
        String videoProductName = getVideoProductName(multiparts);
        for(FileItem item : multiparts){
            if(!item.isFormField()) {
                try {
                    String name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                    String[] nameParts = name.split("\\.");
                    String format = nameParts[nameParts.length - 1];
                    item.write(new File(directory + videoProductName + "." + format));
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}