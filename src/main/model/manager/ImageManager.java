package main.model.manager;

import main.model.entity.User;
import main.model.entity.VideoProduct;
import main.model.exception.ImageFormatException;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code ImageManager} used for saving and removing images.
 */

public class ImageManager {
    private final static Logger logger = Logger.getLogger("ManagerLogger");
    /**
     * Form home slider image list.
     * @return List with filenames of images.
     */
    public static List<String> formHomeSliderImagesList() {
        File directory = new File(PathsManager.getProperty("frames"));
        File[] files = directory.listFiles();
        List<String> pictureFileNames = new ArrayList<String>();
        for (File file : files) {
            pictureFileNames.add(file.getName());
        }
        return pictureFileNames;
    }

    /**
     * Remove entity.
     * @param path value represents filename of image to remove.
     */
    public static void removeHomeSliderImage(String path) {
        if (path == null || path.equals("")) return;
        File file = new File(PathsManager.getProperty("frames") + path);
        file.delete();
    }

    /**
     * Save video product poster.
     * @param multiparts incoming data.
     * @param videoProduct entity.
     */
    public static void savePoster(List<FileItem> multiparts, VideoProduct videoProduct) {
        String directory = PathsManager.getProperty("posters");
        for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                String name = null;
                try {
                    name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                String[] nameParts = name.split("\\.");
                String format = nameParts[nameParts.length - 1];
                if (!format.equals("jpg")) throw new ImageFormatException("Image should have .jpg format!");
                if (videoProduct.getPosterFileName() == null) {
                    String videoProductName = videoProduct.getName().toLowerCase().replaceAll(" ", "-");
                    videoProduct.setPosterFileName(videoProductName.hashCode() + name.hashCode() + "." + format);
                    try {
                        item.write(new File(directory + File.separator + videoProduct.getPosterFileName()));
                    } catch (Exception e) {
                        logger.error(e);
                    }
                } else {
                    File file = new File(directory + videoProduct.getPosterFileName());
                    if (file.exists()) file.delete();
                    try {
                        item.write(new File(directory + videoProduct.getPosterFileName()));
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            }
        }
    }

    public static void removePoster(VideoProduct videoProduct) {
        if (videoProduct != null && videoProduct.getPosterFileName() != null) {
            File file = new File(PathsManager.getProperty("posters") + videoProduct.getPosterFileName());
            file.delete();
        }
    }

    /**
     * Save profile photo.
     *
     * @param item incoming data.
     * @param user entity to update.
     */
    public static void saveProfilePhoto(FileItem item, User user) {
        String directory = PathsManager.getProperty("userProfilePhotos");
        if (!item.isFormField()) {
            String name = null;
            try {
                name = new String(item.getName().getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e);
            }
            if (name.length() != 0) {
                String[] nameParts = name.split("\\.");
                String format = nameParts[nameParts.length - 1];
                if (!format.equals("jpg")) throw new ImageFormatException("Image should have .jpg format!");
                File file = new File("/img/user/" + user.getLogin() + "." + format);
                if (file.exists())
                    file.delete();
                try {
                    item.write(new File(directory + user.getLogin() + "." + format));
                } catch (Exception e) {
                    logger.error(e);
                }
                user.setProfilePhoto(user.getLogin() + "." + format);
            }
        }
    }
}
