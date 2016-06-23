package main.controller;

import main.model.command.*;

public enum RequestType {
    LOGIN("Login", new LoginCommand()),
    REGISTRATION("Registration", new RegistrationCommand()),
    CHANGE_PASSWORD("changePassword", new ChangePasswordCommand()),
    EDIT_PROFILE("editProfile", new EditProfileCommand()),
    EXIT("exit", new ExitCommand()),
    USER_PAGE("userPage", new UserPageCommand()),
    USER_STATUS_UP("userStatusUp", new UserStatusUpCommand()),
    USER_STATUS_DOWN("userStatusDown", new UserStatusDownCommand()),
    USER_BAN("userBan", new UserBanCommand()),
    USER_REMOVE_BAN("userRemoveBan", new UserRemoveBanCommand()),
    USER_LIST("userList", new UserListCommand()),
    FILM_PAGE("filmPage", new FilmPageCommand()),
    ADD_FILM("addFilm", new AddFilmCommand()),
    PUT_FILM_MARK("putFilmMark", new PutFilmMarkCommand()),
    PUT_FILM_COMMENT("putFilmComment", new PutFilmCommentCommand()),
    FILM_LIST("filmList", new FilmListCommand()),
    FILM_EDIT_PAGE("filmEditPage", new FilmEditPageCommand()),
    FILM_EDIT("editFilm", new FilmEditCommand()),
    FILM_REMOVE("filmRemove", new FilmRemoveCommand()),
    TVSERIES_PAGE("tvseriesPage", new TVSeriesPageCommand()),
    ADD_TVSERIES("addTVSeries", new AddTVSeriesCommand()),
    PUT_TVSERIES_MARK("putTVSeriesMark", new PutTVSeriesMarkCommand()),
    CHECK_TVSERIES_MARK_EXISTS("checkTVSeriesMarkExists", new CheckTVSeriesMarkExistsCommand()),
    PUT_TVSERIES_COMMENT("putTVSeriesComment", new PutTVSeriesCommentCommand()),
    CHECK_FILM_MARK_EXISTS("checkFilmMarkExists", new CheckFilmMarkExistsCommand()),
    TVSERIES_LIST("tvseriesList", new TVSeriesListCommand()),
    TVSERIES_EDIT_PAGE("tvseriesEditPage", new TVSeriesEditPageCommand()),
    TVSERIES_EDIT("editTVSeries", new TVSeriesEditCommand()),
    TVSERIES_REMOVE("tvseriesRemove", new TVSeriesRemoveCommand()),
    SEARCH("search", new SearchCommand()),
    SET_LOCALE("setLocale", new SetLocaleCommand()),
    HOME_SLIDER_IMAGES_PAGE("homeSliderImagesPage", new HomeSliderImagePageCommand()),
    ADD_HOME_SLIDER_IMAGE("addHomeSliderImage", new AddHomeSliderImageCommand()),
    REMOVE_HOME_SLIDER_IMAGE("removeHomeSliderImage", new RemoveHomeSliderImageCommand());

    private final String value;
    private final ActionCommand command;

    RequestType(String type, ActionCommand command) {
        this.value = type;
        this.command = command;
    }

    public static RequestType fromValue(String value) {
        for (RequestType c: RequestType.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    public ActionCommand getCurrentRequest() {
        return command;
    }
}
