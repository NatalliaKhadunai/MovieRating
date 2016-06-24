package main.model.command;

import main.controller.Page;
import main.model.dao.FilmDAO;
import main.model.dao.TVSeriesDAO;
import main.model.entity.Film;
import main.model.entity.TVSeries;
import main.model.entity.VideoProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SearchCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String page = null;
        String searchText = new String(request.getParameter("searchText").getBytes("ISO-8859-1"), "UTF-8");
        List<VideoProduct> resultList = formResultList(searchText);
        page = Page.SEARCH_PAGE.getPagePath();
        int maxPageNo = resultList.size() / 10 + 1;
        if (resultList.size() > 10) {
            if (request.getParameter("pageNo") == null) {
                resultList = resultList.subList(0, 10);
                request.setAttribute("pageNo", 1);
                request.setAttribute("maxPageNo", maxPageNo);
            } else {
                int pageNo = Integer.parseInt(request.getParameter("pageNo"));
                if (pageNo == maxPageNo) resultList = resultList.subList((pageNo - 1) * 10 + 1, resultList.size() - 1);
                else resultList = resultList.subList((pageNo - 1) * 10 + 1, (pageNo - 1) * 10 + 11);
                request.setAttribute("pageNo", pageNo);
                request.setAttribute("maxPageNo", maxPageNo);
            }
        }
        request.setAttribute("searchList", resultList);
        request.setAttribute("searchText", searchText);
        return page;
    }

    private List<Film> getAllFilms(String filmTitle) {
        FilmDAO filmDAO = new FilmDAO();
        List<Film> filmList = filmDAO.getAllEntities(filmTitle);
        filmDAO.closeConnection();
        return filmList;
    }

    private List<TVSeries> getAllTVSeries(String tvseriesTitle) {
        TVSeriesDAO tvSeriesDAO = new TVSeriesDAO();
        List<TVSeries> tvSeriesList = tvSeriesDAO.getAllEntities(tvseriesTitle);
        tvSeriesDAO.closeConnection();
        return tvSeriesList;
    }

    private List<VideoProduct> formResultList(String searchText) {
        List<Film> filmList = getAllFilms(searchText);
        List<TVSeries> tvSeriesList = getAllTVSeries(searchText);
        List<VideoProduct> resultList = new ArrayList<>();
        resultList.addAll(filmList);
        resultList.addAll(tvSeriesList);
        return resultList;
    }
}