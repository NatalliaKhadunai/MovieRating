package main.controller;

/**
 * Enum {@code Page} represents all the pages in the system.
 */

public enum Page {
    /** Main controller-servlet */
    SERVICE_SERVLET("/RatingServlet"),
    /** Main page */
    MAIN("/main.jsp"),
    /** Sign up page */
    SIGN_UP_PAGE("/signUp.jsp"),
    /** Search page */
    SEARCH_PAGE("/search.jsp"),
    /** Film page */
    FILM_PAGE("/film.jsp"),
    /** TV Series page */
    TVSERIES_PAGE("/tvseries.jsp"),
    /** Logged user page */
    LOGGED_USER_PAGE("/loggedUser.jsp"),
    /** User page */
    USER_PAGE("/user.jsp"),
    /** Change password page */
    CHANGE_PASSWORD_PAGE("/changePassword.jsp"),
    /** Film edit page */
    EDIT_FILM_PAGE("/editFilm.jsp"),
    /** TV Series edit page */
    EDIT_TVSERIES_PAGE("/editTVSeries.jsp"),
    /** Film process page */
    PROCESS_FILMS_PAGE("/processFilms.jsp"),
    /** TV Series process page */
    PROCESS_TVSERIES_PAGE("/processTVSeries.jsp"),
    /** User process page */
    PROCESS_USERS("/processUsers.jsp"),
    /** Management of home slider images page */
    MANAGE_HOME_SLIDER_IMAGES_PAGE("/manageHomeSliderImages.jsp");

    /** Page path */
    private final String value;

    Page(String value) {
        this.value = value;
    }

    /**
     * Return page path.
     * @return page path.
     */
    public String getPagePath() {
        return value;
    }
}
