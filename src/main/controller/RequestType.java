package main.controller;

import main.model.command.*;

/**
 * Enum {@code RequestType} represents all the requests in the system.
 */

public enum RequestType {
    /** Login request */
    LOGIN("Login", new LoginCommand()),
    /** Sign Up request */
    REGISTRATION("Registration", new RegistrationCommand()),
    /** Change password request */
    CHANGE_PASSWORD("changePassword", new ChangePasswordCommand()),
    /** Edit profile request */
    EDIT_PROFILE("editProfile", new EditProfileCommand()),
    /** Delete page request */
    DELETE_PAGE("deletePage", new DeletePageCommand()),
    /** Exit request */
    EXIT("exit", new ExitCommand()),
    /** User page request */
    USER_PAGE("userPage", new UserPageCommand()),
    /** User status up request */
    USER_STATUS_UP("userStatusUp", new UserStatusUpCommand()),
    /** User status down request */
    USER_STATUS_DOWN("userStatusDown", new UserStatusDownCommand()),
    /** User ban request */
    USER_BAN("userBan", new UserBanCommand()),
    /** User remove ban request */
    USER_REMOVE_BAN("userRemoveBan", new UserRemoveBanCommand()),
    /** User make admin request */
    USER_MAKE_ADMIN("userMakeAdmin", new UserMakeAdminCommand()),
    /** User remove admin status request */
    USER_REMOVE_ADMIN_STATUS("userRemoveAdminStatus", new UserRemoveAdminStatusCommand()),
    /** User list request */
    USER_LIST("userList", new UserListCommand()),
    /** Film page request */
    FILM_PAGE("filmPage", new FilmPageCommand()),
    /** Add film request */
    ADD_FILM("addFilm", new AddFilmCommand()),
    /** Put film mark request */
    PUT_FILM_MARK("putFilmMark", new PutFilmMarkCommand()),
    /** Put film comment request */
    PUT_FILM_COMMENT("putFilmComment", new PutFilmCommentCommand()),
    /** Film list request */
    FILM_LIST("filmList", new FilmListCommand()),
    /** Film edit page request */
    FILM_EDIT_PAGE("filmEditPage", new FilmEditPageCommand()),
    /** Film edit request */
    FILM_EDIT("editFilm", new FilmEditCommand()),
    /** Film remove request */
    FILM_REMOVE("filmRemove", new FilmRemoveCommand()),
    /** TV Series page request */
    TVSERIES_PAGE("tvseriesPage", new TVSeriesPageCommand()),
    /** Add TV Series request */
    ADD_TVSERIES("addTVSeries", new AddTVSeriesCommand()),
    /** Put TV Series mark */
    PUT_TVSERIES_MARK("putTVSeriesMark", new PutTVSeriesMarkCommand()),
    /* Check TV Series mark exists */
    CHECK_TVSERIES_MARK_EXISTS("checkTVSeriesMarkExists", new CheckTVSeriesMarkExistsCommand()),
    /** Put TV Series comment request */
    PUT_TVSERIES_COMMENT("putTVSeriesComment", new PutTVSeriesCommentCommand()),
    /** Check film mark exists request */
    CHECK_FILM_MARK_EXISTS("checkFilmMarkExists", new CheckFilmMarkExistsCommand()),
    /** TV Series list */
    TVSERIES_LIST("tvseriesList", new TVSeriesListCommand()),
    /** TV Series edit page request */
    TVSERIES_EDIT_PAGE("tvseriesEditPage", new TVSeriesEditPageCommand()),
    /** TV Series edit page */
    TVSERIES_EDIT("editTVSeries", new TVSeriesEditCommand()),
    /** TV Series remove */
    TVSERIES_REMOVE("tvseriesRemove", new TVSeriesRemoveCommand()),
    /** Search request */
    SEARCH("search", new SearchCommand()),
    /** Set locale request */
    SET_LOCALE("setLocale", new SetLocaleCommand()),
    /** Home slider images page request */
    HOME_SLIDER_IMAGES_PAGE("homeSliderImagesPage", new HomeSliderImagePageCommand()),
    /** Add home slider image request */
    ADD_HOME_SLIDER_IMAGE("addHomeSliderImage", new AddHomeSliderImageCommand()),
    /** Remove home slider image request */
    REMOVE_HOME_SLIDER_IMAGE("removeHomeSliderImage", new RemoveHomeSliderImageCommand());

    /** Request string representation */
    private final String value;
    /** Command associated with request */
    private final ActionCommand command;

    RequestType(String type, ActionCommand command) {
        this.value = type;
        this.command = command;
    }

    /**
     * Define RequestType by string representation.
     * @param value string representation of request.
     * @return defined RequestType.
     */
    public static RequestType fromValue(String value) {
        for (RequestType c: RequestType.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    /**
     * Return current ActionCommand specified by field command.
     * @return current ActionCommand.
     */
    public ActionCommand getCurrentRequest() {
        return command;
    }
}
