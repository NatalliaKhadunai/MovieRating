package main.model;

import main.model.entity.VideoProduct;

import java.util.Comparator;

public class VideoProductComparatorByComment implements Comparator<VideoProduct> {
    /**
     * Compares two {@code VideoProduct} objects by number of comments.
     * @param product1 the first {@code VideoProduct} object to be compared.
     * @param product2 the second {@code VideoProduct} object to be compared.
     * @return the value {@code 0} if the argument object is equal to
     *          this object; a value less than {@code 0} if this object
     *          has less comments than the argument object; and a
     *          value greater than {@code 0} if this object has
     *          more comments than the argument object.
     */
    @Override
    public int compare(VideoProduct product1, VideoProduct product2) {
        if (product1.getNumOfComments() > product2.getNumOfComments()) return -1;
        if (product1.getNumOfComments() < product2.getNumOfComments()) return 1;
        else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
