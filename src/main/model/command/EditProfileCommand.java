package main.model.command;

import main.controller.Page;
import main.model.exception.ImageFormatException;
import main.model.manager.ImageManager;
import main.model.manager.PathsManager;
import main.model.dao.UserDAO;
import main.model.entity.User;
import main.ProfileField;
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

/**
 * Command to update user entity.
 */

public class EditProfileCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = null;
            try {
                multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            } catch (FileUploadException e) {
                logger.error(e);
            }
            UserDAO userDAO = new UserDAO();
            User user = (User) request.getSession(false).getAttribute("loggedUser");
            List<ProfileField> updatedFields = setUpdatedFields(multiparts, user);
            userDAO.updateEntity(user, updatedFields);
            userDAO.closeConnection();
            try {
                response.sendRedirect(Page.LOGGED_USER_PAGE.getPagePath());
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Define fields to update.
     *
     * @param multiparts incoming data.
     * @param user       entity to update.
     * @return List with fields to update.
     */
    private List<ProfileField> setUpdatedFields(List<FileItem> multiparts, User user) {
        List<ProfileField> updatedFields = new ArrayList<>();
        for (FileItem item : multiparts) {
            if (item.isFormField()) {
                try {
                    ProfileField fieldName = ProfileField.fromValue(item.getFieldName());
                    String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                    if (fieldName != null && value.length() != 0) {
                        switch (fieldName) {
                            case EMAIL: {
                                user.setEmail(value);
                                updatedFields.add(ProfileField.EMAIL);
                            }
                            break;
                            case FULL_NAME: {
                                user.setName(value);
                                updatedFields.add(ProfileField.FULL_NAME);
                            }
                            break;
                            case SEX: {
                                user.setSex(value.charAt(0));
                                updatedFields.add(ProfileField.SEX);
                            }
                            break;
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
            } else {
                ImageManager.saveProfilePhoto(item, user);
                updatedFields.add(ProfileField.PROFILE_PHOTO);
            }
        }
        return updatedFields;
    }


}