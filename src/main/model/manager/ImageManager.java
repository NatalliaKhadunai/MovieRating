package main.model.manager;

import main.model.entity.User;
import main.model.entity.VideoProduct;
import main.model.exception.ImageFormatException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.comparator.PathFileComparator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
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
     * Save frame.
     * @param multiparts incoming data.
     */
    public static void saveFrame(List<FileItem> multiparts) {
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

    /**
     * Return video product name.
     * @param multiparts incoming data.
     * @return video product name.
     */
    private static String getVideoProductName(List<FileItem> multiparts) {
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
                File file = new File(directory + user.getLogin().hashCode() + "." + format);
                if (file.exists())
                    file.delete();
                try {
                    item.write(new File(directory + user.getLogin().hashCode() + "." + format));
                } catch (Exception e) {
                    logger.error(e);
                }
                user.setProfilePhoto(user.getLogin().hashCode() + "." + format);
            }
        }
    }

    public static void removeProfilePhoto(User user) {
        if (!checkImageIsDefault(user.getProfilePhoto())) {
            String directory = PathsManager.getProperty("userProfilePhotos");
            File file = new File(directory + user.getProfilePhoto());
            if (file.exists()) file.delete();
        }
    }

    private static boolean checkImageIsDefault(String fileName) {
        Collection<Object> enumeration = DefaultImageManager.getValues();
        for (Object obj : enumeration) {
            if (obj.toString().equals(fileName)) return true;
        }
        return false;
    }
}
