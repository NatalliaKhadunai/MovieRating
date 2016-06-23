package main.model.entity;

public class VideoProduct implements Comparable<VideoProduct> {
    protected int ID;
    protected String name;
    protected String description;
    protected float rating;
    protected String posterFileName;
    protected int numOfComments;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterFileName() {
        return posterFileName;
    }

    public void setPosterFileName(String posterFileName) {
        this.posterFileName = posterFileName;
    }

    public int getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(int numOfComments) {
        this.numOfComments = numOfComments;
    }

    @Override
    public int compareTo(VideoProduct o) {
        VideoProduct videoProduct = (VideoProduct) o;
        if (this.numOfComments > videoProduct.numOfComments) return -1;
        if (this.numOfComments < videoProduct.numOfComments) return 1;
        else return 0;
    }
}
