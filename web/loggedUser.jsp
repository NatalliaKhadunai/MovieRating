<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/customTag.tld" prefix="ct" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/roundedImageLarge.css" rel="stylesheet">
</head>

<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <div class="rounded-image" style="background-image: url('img/user/${sessionScope.loggedUser.profilePhoto}');">
        </div>
    </div>
    <div class="row">
        <div class="center-block" style="width: 300px;">
            <h1>${sessionScope.loggedUser.login}</h1>
            <h4>
                <fmt:message key="label.fullName" bundle="${rb}"/>: ${sessionScope.loggedUser.name}
            </h4>
            <h4>
                <fmt:message key="label.email" bundle="${rb}"/>: ${sessionScope.loggedUser.email}
            </h4>
            <h4>
                <fmt:message key="label.sex" bundle="${rb}"/>: ${sessionScope.loggedUser.sex}
            </h4>
            <h4>
                <fmt:message key="label.status" bundle="${rb}"/>
                <ct:statusTag status="${sessionScope.loggedUser.statusName}"></ct:statusTag>
            </h4>
            <h4><fmt:message key="label.statusCoefficient" bundle="${rb}"/>: ${sessionScope.loggedUser.statusCoefficient}
            </h4>
            <div style="text-align: center;">
                <a class="btn btn-default" href="/editProfile.jsp">
                    <fmt:message key="button.editProfile" bundle="${rb}"/>
                </a>
                <a class="btn btn-default" href="/RatingServlet?requestType=deletePage">
                    <fmt:message key="button.deleteProfile" bundle="${rb}"/>
                </a>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;">
        <c:choose>
            <c:when test="${sessionScope.loggedUser.statusName eq 'ADMIN'}">
                <div class="center-block" style="width: 600px; text-align: center;">
                    <a class="btn btn-default" href="/addFilm.jsp">
                        <fmt:message key="button.addFilm" bundle="${rb}"/>
                    </a>
                    <a class="btn btn-default" href="/RatingServlet?requestType=filmList">
                        <fmt:message key="button.processFilms" bundle="${rb}"/>
                    </a>
                    <a class="btn btn-default" href="/addTVSeries.jsp">
                        <fmt:message key="button.addTVSeries" bundle="${rb}"/>
                    </a>
                    <a class="btn btn-default" href="/RatingServlet?requestType=tvseriesList">
                        <fmt:message key="button.processTVSeries" bundle="${rb}"/>
                    </a>
                    <a class="btn btn-default" href="/RatingServlet?requestType=homeSliderImagesPage">
                        <fmt:message key="button.manageHomeSliderImages" bundle="${rb}"/>
                    </a>
                    <a class="btn btn-default" href="/RatingServlet?requestType=userList">
                        <fmt:message key="button.processUsers" bundle="${rb}"/>
                    </a>
                </div>
            </c:when>
        </c:choose>
    </div>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div><!-- /.container -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-2.2.3.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>

</body></html>