package main.controller;

public enum Page {
    SERVICE_SERVLET("/RatingServlet"),
    MAIN("/main.jsp"),
    SIGN_UP_PAGE("/signUp.jsp"),
    SEARCH_PAGE("/search.jsp"),
    FILM_PAGE("/film.jsp"),
    TVSERIES_PAGE("/tvseries.jsp"),
    LOGGED_USER_PAGE("/loggedUser.jsp"),
    USER_PAGE("/user.jsp"),
    CHANGE_PASSWORD_PAGE("/changePassword.jsp"),
    EDIT_FILM_PAGE("/editFilm.jsp"),
    EDIT_TVSERIES_PAGE("/editTVSeries.jsp"),
    PROCESS_FILMS_PAGE("/processFilms.jsp"),
    PROCESS_TVSERIES_PAGE("/processTVSeries.jsp"),
    PROCESS_USERS("/processUsers.jsp"),
    MANAGE_HOME_SLIDER_IMAGES_PAGE("/manageHomeSliderImages.jsp");
    private final String value;

    Page(String value) {
        this.value = value;
    }

    public static Page fromValue(String value) {
        for (Page c: Page.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    public String getPagePath() {
        return value;
    }
}
