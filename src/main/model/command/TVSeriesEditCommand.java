package main.model.command;

import main.controller.Page;
import main.model.manager.PathsManager;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import main.TVSeriesField;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TVSeriesEditCommand implements ActionCommand {
    private final Logger logger = Logger.getLogger("CommandLogger");
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        int tvseriesID = Integer.valueOf(request.getParameter("tvseriesID"));
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
                TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesID);
                List<TVSeriesField> updatedFields = getUpdatedFields(multiparts);
                updateFields(multiparts, updatedFields, tvSeries);
                savePoster(multiparts, tvSeries);
                tvSeriesDAO.updateEntity(tvSeries, updatedFields);
                tvSeriesDAO.closeConnection();
                page = Page.SERVICE_SERVLET.getPagePath() + "?requestType=tvseriesList";
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return page;
    }

    private List<TVSeriesField> getUpdatedFields(List<FileItem> multiparts) {
        List<TVSeriesField> updatedFields = new ArrayList<>();
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    String fieldName = item.getFieldName();
                    if (fieldName.equals("field")) {
                        TVSeriesField value = TVSeriesField.fromValue(new String(item.getString().getBytes("ISO-8859-1"), "UTF-8"));
                        updatedFields.add(value);
                    }
                }
                catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
            }
        }
        return updatedFields;
    }

    private void updateFields(List<FileItem> multiparts, List<TVSeriesField> updatedFields, TVSeries tvSeries) {
        for(FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    TVSeriesField fieldName = TVSeriesField.fromValue(item.getFieldName());
                    String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                    if (fieldName != null && updatedFields.contains(fieldName) && value.length() != 0) {
                        switch (fieldName) {
                            case NAME: {
                                tvSeries.setName(value);
                            }
                            break;
                            case RELEASE_YEAR: {
                                tvSeries.setReleaseYear(Integer.valueOf(value));
                            }
                            break;
                            case END_YEAR: {
                                tvSeries.setEndYear(Integer.valueOf(value));
                            }
                            break;
                            case NUMBER_OF_SEASONS: {
                                tvSeries.setNumOfSeasons(Integer.valueOf(value));
                            }
                            break;
                            case DESCRIPTION: {
                                tvSeries.setDescription(value);
                            }
                            break;
                            case RATING: {
                                tvSeries.setRating(Float.valueOf(value));
                            }
                            break;
                        }
                    }
                }
                catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
            }
        }
    }

    private void savePoster(List<FileItem> multiparts, TVSeries tvSeries) {
        String directory = PathsManager.getProperty("posters");
        for (FileItem item : multiparts){
            if(!item.isFormField()) {
                try {
                    String name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                    if (name.length() != 0) {
                        File file = new File(directory + tvSeries.getPosterFileName());
                        if (file.exists()) file.delete();
                        item.write(new File(directory + tvSeries.getPosterFileName()));
                    }
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}