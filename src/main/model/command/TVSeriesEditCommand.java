package main.model.command;

import main.controller.Page;
import main.model.exception.ImageFormatException;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.model.dao.TVSeriesDAO;
import main.model.entity.TVSeries;
import main.TVSeriesField;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TVSeriesEditCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        int tvseriesID = Integer.valueOf(request.getParameter("tvseriesID"));
        if(ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = null;
            try {
                multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            } catch (FileUploadException e) {
                logger.error(e);
            }
            TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
            TVSeries tvSeries = tvSeriesDAO.getEntity(tvseriesID);
            List<TVSeriesField> updatedFields = getUpdatedFields(multiparts);
            updateFields(multiparts, updatedFields, tvSeries);
            ImageManager.savePoster(multiparts, tvSeries);
            tvSeriesDAO.updateEntity(tvSeries, updatedFields);
            tvSeriesDAO.closeConnection();
        }
        try {
            response.sendRedirect(Page.SERVICE_SERVLET.getPagePath() + "?requestType=tvseriesList");
        }
        catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Define fields to update.
     * @param multiparts incoming data.
     * @return List with fields to update.
     */
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

    /**
     * Update user entity fields.
     * @param multiparts incoming data.
     * @param updatedFields list of fields to update.
     * @param tvSeries entity to update.
     */
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
}