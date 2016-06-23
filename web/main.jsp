<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/customTag.tld" prefix="ct" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Movie Rating</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <script src="js/jquery-2.2.3.js"></script>
    <script src="js/bootstrap.js"></script>
    <link href="css/roundedImageMedium.css" rel="stylesheet">
</head>

<body>

<%@ include file="navbar.jsp"%>

<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
        <c:forEach items="${homeSliderImages}" var="sliderImage" varStatus="status">
            <c:choose>
                <c:when test="${status.count == 1}">
                    <div class="item active">
                        <a href="#">
                            <img src="img/frame/${sliderImage}" alt="ALT">
                            <div class="carousel-caption">
                                <c:set var="string1" value="${sliderImage}"/>
                                <c:set var="string2" value="${fn:replace(string1,'.jpg', '')}" />
                                <h4>${string2}</h4>
                            </div>
                        </a>
                    </div>
                </c:when>
                <c:when test="${status.count != 1}">
                    <div class="item">
                        <a href="#">
                            <img src="img/frame/${sliderImage}" alt="ALT">
                            <div class="carousel-caption">
                                <c:set var="string1" value="${sliderImage}"/>
                                <c:set var="string2" value="${fn:replace(string1,'.jpg', '')}" />
                                <h4>${string2}</h4>
                            </div>
                        </a>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<div class="container">

    <div class="row" style="margin-top: 20px;text-align: center;">
        <h2><fmt:message key="label.topCommented" bundle="${rb}"/></h2>
    </div>

    <div class="row" style="margin-top: 10px;">
        <c:forEach items="${topCommentedList}" var="topCommentedItem">
            <div class="col-md-4" style="text-align: center;">
                <div class="rounded-image" style="background-image: url('img/video product/${topCommentedItem.posterFileName}');">
                </div>
                <c:choose>
                    <c:when test="${topCommentedItem['class'].simpleName eq 'Film'}">
                        <h3><a href="/RatingServlet?requestType=filmPage&filmTitle=${topCommentedItem.name}">${topCommentedItem.name}</a></h3>
                    </c:when>
                    <c:when test="${topCommentedItem['class'].simpleName eq 'TVSeries'}">
                        <h3><a href="/RatingServlet?requestType=tvseriesPage&tvseriesTitle=${topCommentedItem.name}">${topCommentedItem.name}</a></h3>
                    </c:when>
                </c:choose>
                <p><fmt:message key="label.numOfCommentOverPastWeek" bundle="${rb}"/>: ${topCommentedItem.numOfComments}</p>
            </div>
        </c:forEach>
    </div>

    <div class="row" style="margin-top: 20px;text-align: center;">
            <div class="col-md-6">
                <h4><fmt:message key="label.topFilms" bundle="${rb}"/></h4>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th><fmt:message key="label.name" bundle="${rb}"/></th>
                            <th><fmt:message key="label.rating" bundle="${rb}"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <ct:topRatedFilms/>
                    </tbody>
                </table>
            </div>


            <div class="col-md-6">
                <h4><fmt:message key="label.topTVSeries" bundle="${rb}"/></h4>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th><fmt:message key="label.name" bundle="${rb}"/></th>
                            <th><fmt:message key="label.rating" bundle="${rb}"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <ct:topRatedTVSeries/>
                    </tbody>
                </table>
            </div>
    </div>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div> <!-- /container -->

</body></html>