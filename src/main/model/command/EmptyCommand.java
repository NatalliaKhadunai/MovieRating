package main.model.command;

import main.controller.Page;
import main.model.VideoProductComparatorByComment;
import main.model.manager.PathsManager;
import main.model.dao.FilmDAO;
import main.model.dao.ImageDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.VideoProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command to form home page.
 */

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        ImageDAO imageDAO = new ImageDAO();
        List<String> images = imageDAO.formHomeSliderImagesList();
        imageDAO.closeConnection();
        List<VideoProduct> topCommentedVideoProducts = formMostCommentedVideoList();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("locale") == null) {
            session.setAttribute("locale", "EN");
        }
        if (session.getAttribute("isLoggedIn") == null ) session.setAttribute("isLoggedIn", false);
        request.setAttribute("homeSliderImages", images);
        request.setAttribute("topCommentedList", topCommentedVideoProducts);
        page = Page.MAIN.getPagePath();
        return page;
    }

    /**
     * Form most comment video product list.
     * @return List with VideoProduct entities.
     */
    private List<VideoProduct> formMostCommentedVideoList() {
        List<VideoProduct> films = getMostCommentedFilms();
        List<VideoProduct> tvseries = getMostCommentedTVSeries();
        List<VideoProduct> videoProductList = new ArrayList<>();
        videoProductList.addAll(films);
        videoProductList.addAll(tvseries);
        Collections.sort(videoProductList, new VideoProductComparatorByComment());
        List<VideoProduct> resultList = videoProductList.subList(0,3);
        return resultList;
    }

    /**
     * Return most commented films.
     * @return List with most commented Film entities.
     */
    private List<VideoProduct> getMostCommentedFilms() {
        FilmDAO filmDAO = new FilmDAO();
        List<VideoProduct> filmTopCommented = filmDAO.getTopCommented();
        filmDAO.closeConnection();
        return filmTopCommented;
    }

    /**
     * Return most commented tv series.
     * @return List with most commented TVSeries entities.
     */
    private List<VideoProduct> getMostCommentedTVSeries() {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        List<VideoProduct> tvSeriesTopCommented = tvSeriesDAO.getTopCommented();
        tvSeriesDAO.closeConnection();
        return tvSeriesTopCommented;
    }
}
