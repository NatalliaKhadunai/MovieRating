package main.model.entity;

/**
 * Class {@code TVSeries} represents TVSeries entity.
 */

public class TVSeries extends VideoProduct {
    /** {@code TVSeries} release year */
    private int releaseYear;
    /** {@code TVSeries} end year */
    private int endYear;
    /** {@code TVSeries} number of seasons */
    private int numOfSeasons;

    /**
     * Returns release year value for the object.
     * @return release year value for the object.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets release year value for the object.
     * @param releaseYear value which is set in the field release year.
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Returns end year value for the object.
     * @return end year value for the object.
     */
    public int getEndYear() {
        return endYear;
    }

    /**
     * Sets end year value for the object.
     * @param endYear value which is set in the field end year.
     */
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    /**
     * Returns number of seasons value for the object.
     * @return number of seasons value for the object.
     */
    public int getNumOfSeasons() {
        return numOfSeasons;
    }

    /**
     * Sets number of seasons value for the object.
     * @param numOfSeasons value which is set in the field number of seasons.
     */
    public void setNumOfSeasons(int numOfSeasons) {
        this.numOfSeasons = numOfSeasons;
    }
}
