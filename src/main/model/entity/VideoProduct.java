package main.model.entity;
/**
 * Class {@code VideoProduct} is the root class for {@link main.model.entity.Film}
 * and {@link main.model.entity.TVSeries} classes.
 */

public class VideoProduct {
    /** The value is used to identify {@code VideoProduct} object */
    protected int ID;
    /** {@code VideoProduct} name */
    protected String name;
    /** {@code VideoProduct} description */
    protected String description;
    /** {@code VideoProduct} rating */
    protected float rating;
    /** {@code VideoProduct} poster filename */
    protected String posterFileName;
    /** {@code VideoProduct} number of comments */
    protected int numOfComments;

    /**
     * Returns ID value for the object.
     * @return ID value for the object.
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets ID value for the object.
     * @param ID value which is set in the field ID.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Sets name value for the object.
     * @param name value which is set in the field name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns name value for the object.
     * @return name value for the object.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets rating value for the object.
     * @param rating value which is set in the field rating.
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Returns rating value for the object.
     * @return rating value for the object.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets description value for the object.
     * @param description value which is set in the field description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns description value for the object.
     * @return description value for the object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns poster filename value for the object.
     * @return poster filename value for the object.
     */
    public String getPosterFileName() {
        return posterFileName;
    }

    /**
     * Sets poster filename for the object.
     * @param posterFileName value which is set in the field poster filename.
     */
    public void setPosterFileName(String posterFileName) {
        this.posterFileName = posterFileName;
    }

    /**
     * Returns number of comments for the object.
     * @return number of comments for the object.
     */
    public int getNumOfComments() {
        return numOfComments;
    }

    /**
     * Sets number of comments for the object.
     * @param numOfComments value which is set in the field number of comments.
     */
    public void setNumOfComments(int numOfComments) {
        this.numOfComments = numOfComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoProduct)) return false;

        VideoProduct that = (VideoProduct) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + (posterFileName != null ? posterFileName.hashCode() : 0);
        result = 31 * result + numOfComments;
        return result;
    }
}
