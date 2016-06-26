<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/videoProductPage.css" rel="stylesheet">
    <script src="js/filmPage.js"></script>
</head>

<body onload="initialization('<%=session.getAttribute("isLoggedIn")%>', '${sessionScope.loggedUser.statusName}',
        '${sessionScope.loggedUser.login}', '${film.name}')">

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <img src="img/video product/${film.posterFileName}" class="col-md-3" style="position: absolute;">
        <h1 class="col-md-6 col-md-offset-4">${film.name}</h1>
        <p class="col-md-7 col-md-offset-4">${film.description}</p>
        <h4 class="col-md-9 col-md-offset-4"><fmt:message key="label.releaseDate" bundle="${rb}"/>:
            <fmt:formatDate value="${film.releaseDate}"/>
        </h4>
        <h3 class="col-md-1 col-md-offset-4"><fmt:message key="label.rating" bundle="${rb}"/>:</h3>
        <h2 class="col-md-1 rating">${film.rating}</h2></p>
        <h3 class="col-md-8 col-md-offset-4"><fmt:message key="label.rateThis" bundle="${rb}"/>:</h3>
        <div class="col-md-8 col-md-offset-4" id="ratingContainer">
            <span class="glyphicon glyphicon-star" aria-hidden="true" style="font-size: 3rem;" onmouseover="changeColorsOver(this)" onclick="putMark(this)" id="1"></span>
        </div>
    </div>

    <div class="row" style="margin-top: 85px;">
        <h3><fmt:message key="label.leaveYourComment" bundle="${rb}"/></h3>
        <textarea class="form-control" class="col-md-4" name="commentContent" id="commentContent" onkeyup="commentLength('<fmt:message key="label.symbols" bundle="${rb}"/>')"></textarea>
        <br>
        <span id="numOfSymbols">0 <fmt:message key="label.symbols" bundle="${rb}"/></span>
        <br>
        <br>
        <button class="btn btn-primary" onclick="putComment()">
            <fmt:message key="button.addComment" bundle="${rb}"/>
        </button>
    </div>

    <h2 style="margin-top: 40px;"><fmt:message key="label.comments" bundle="${rb}"/>:</h2>
    <dl id="commentsDL">
        <c:forEach items="${commentList}" var="comment" varStatus="status">
            <div class="row" style="margin-top: 20px;">
                <dt class="commentInfo">
                    <a href="/RatingServlet?requestType=userPage&userLogin=${comment.userLogin}">${comment.userLogin}</a>
                    on
                    <fmt:formatDate value="${comment.date}" type="date"/>
                    <fmt:formatDate value="${comment.date}" type="time" timeStyle="short"/>
                </dt>
                <dd>${comment.content}</dd>
            </div>
        </c:forEach>
    </dl>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div><!-- /.container -->

<script src="js/jquery-2.2.3.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-2.2.3.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>