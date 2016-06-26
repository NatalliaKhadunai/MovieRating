package main.model.command;

import main.controller.Page;
import main.model.manager.PathsManager;
import main.model.dao.UserDAO;
import main.model.entity.User;
import main.ProfileField;
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

/**
 * Command to update user entity.
 */

public class EditProfileCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        if(ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                UserDAO userDAO = new UserDAO();
                User user = (User) request.getSession(false).getAttribute("loggedUser");
                List<ProfileField> updatedFields = setUpdatedFields(multiparts, user);
                saveProfilePhoto(multiparts, user);
                userDAO.updateEntity(user, updatedFields);
                userDAO.closeConnection();
                page = Page.LOGGED_USER_PAGE.getPagePath();
            }
            catch (Exception  e) {
                logger.error(e);
            }
        }
        return page;
    }

    /**
     * Define fields to update.
     * @param multiparts incoming data.
     * @param user entity to update.
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
                }
                catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
            }
        }
        return updatedFields;
    }

    /**
     * Save profile photo.
     * @param multiparts incoming data.
     * @param user entity to update.
     */
    private void saveProfilePhoto(List<FileItem> multiparts, User user) {
        String directory = PathsManager.getProperty("userProfilePhotos");
        for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                try {
                    String name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                    if (name.length() != 0) {
                        String[] nameParts = name.split("\\.");
                        String format = nameParts[nameParts.length - 1];
                        File file = new File(directory + user.getLogin() + "." + format);
                        if (file.exists()) file.delete();
                        item.write(new File(directory + user.getLogin() + "." + format));
                    }
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}