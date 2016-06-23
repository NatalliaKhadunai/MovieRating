<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="author" content="">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/validateTVSeriesAddOrEdit.js"></script>
    <script src="js/enableDisableElement.js"></script>
</head>
<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <form class="col-md-3" action="/RatingServlet?requestType=editTVSeries&tvseriesID=${tvseries.ID}" method="post" enctype="multipart/form-data" onsubmit="return validate(this);">
            <p><fmt:message key="label.tvseriesName" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('name')" value="name">
            <input type="text" name="name" class="form-control" placeholder="${tvseries.name}" disabled>
            <br>
            <p><fmt:message key="label.releaseYear" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('releaseYear')" value="releaseYear">
            <input type="text" name="releaseYear" class="form-control" placeholder="${tvseries.releaseYear}" disabled>
            <br>
            <p><fmt:message key="label.endYear" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('endYear')" value="endYear">
            <input type="text" name="endYear" class="form-control" placeholder="${tvseries.endYear}" disabled>
            <br>
            <p><fmt:message key="label.numOfSeasons" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('numOfSeasons')" value="numOfSeasons">
            <input type="text" name="numOfSeasons" class="form-control" placeholder="${tvseries.numOfSeasons}" disabled>
            <br>
            <p><fmt:message key="label.description" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('description')" value="description">
            <textarea name="description" class="form-control" placeholder="${tvseries.description}" disabled></textarea>
            <br>
            <p><fmt:message key="label.rating" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('rating')" value="rating">
            <input type="text" name="rating" class="form-control" placeholder="${tvseries.rating}" disabled>
            <br>
            <p><fmt:message key="label.poster" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('poster')" value="poster">
            <input type="file" name="poster" disabled>
            <br>
            <input type="submit" name="submit" class="btn btn-primary" value="<fmt:message key="button.add" bundle="${rb}"/>">
        </form>
    </div>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-2.2.3.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>