package main;

public enum FilmField {
    NAME("name"),
    RELEASE_DATE("releaseDate"),
    DESCRIPTION("description"),
    RATING("rating"),
    POSTER_FILENAME("poster");

    private final String value;

    FilmField(String type) {
        this.value = type;
    }

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
