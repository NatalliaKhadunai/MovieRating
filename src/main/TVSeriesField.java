package main;

public enum TVSeriesField {
    NAME("name"),
    RELEASE_YEAR("releaseYear"),
    END_YEAR("endYear"),
    NUMBER_OF_SEASONS("numOfSeasons"),
    DESCRIPTION("description"),
    RATING("rating"),
    POSTER_FILENAME("poster");

    private final String value;

    TVSeriesField(String type) {
        this.value = type;
    }

    public static TVSeriesField fromValue(String value) {
        TVSeriesField tvSeriesField = null;
        for (TVSeriesField c: TVSeriesField.values()) {
            if (c.value.equals(value)) {
                tvSeriesField = c;
            }
        }
        return tvSeriesField;
    }
}
