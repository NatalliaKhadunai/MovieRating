package main;

/**
 * Enum, represents field of TVSeries entity.
 */

public enum TVSeriesField {
    /** Name field. */
    NAME("name"),
    /** Release year field. */
    RELEASE_YEAR("releaseYear"),
    /** End yead field. */
    END_YEAR("endYear"),
    /** Number of seasons field. */
    NUMBER_OF_SEASONS("numOfSeasons"),
    /** Description field. */
    DESCRIPTION("description"),
    /** Rating field. */
    RATING("rating"),
    /** Poster filename field. */
    POSTER_FILENAME("poster");

    /** String representation of field. */
    private final String value;

    TVSeriesField(String type) {
        this.value = type;
    }

    /**
     * Define field from its string representation.
     * @param value, string representation of field.
     * @return define field.
     */
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
