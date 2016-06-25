package main;

/**
 * Enum, represents field of Film entity.
 */

public enum FilmField {
    /** Name field. */
    NAME("name"),
    /** Release date field. */
    RELEASE_DATE("releaseDate"),
    /** Description field. */
    DESCRIPTION("description"),
    /** Rating field. */
    RATING("rating"),
    /**Poster filename field. */
    POSTER_FILENAME("poster");

    /** String representation of field. */
    private final String value;

    FilmField(String type) {
        this.value = type;
    }

    /**
     * Define field from its string representation.
     * @param value, string representation of field.
     * @return define field.
     */
    public static FilmField fromValue(String value) {
        FilmField filmField = null;
        for (FilmField c: FilmField.values()) {
            if (c.value.equals(value)) {
                filmField = c;
            }
        }
        return filmField;
    }
}
