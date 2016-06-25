package main.model.entity;

import java.sql.Date;

/**
 * Class {@code Film} represents Film entity.
 */

public class Film extends VideoProduct {
    /** {@code Film} release date */
    private Date releaseDate;

    /**
     * Returns release date value for the object.
     * @return release date value for the object.
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets release date value for the object.
     * @param releaseDate value which is set in the field release date.
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
