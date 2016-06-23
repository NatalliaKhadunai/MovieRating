<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/validateFilmAddOrEdit.js"></script>
    <script src="js/enableDisableElement.js"></script>
</head>
<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <form class="col-md-3" action="/RatingServlet?requestType=editFilm&filmID=${film.ID}" method="post" enctype="multipart/form-data" onsubmit="return validate(this);">
            <p><fmt:message key="label.filmName" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('name')" value="name">
            <input type="text" name="name" class="form-control" placeholder="${film.name}" disabled>
            <br>
            <p><fmt:message key="label.releaseDate" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('releaseDate')" value="releaseDate">
            <input type="date" name="releaseDate" class="form-control" value="${film.releaseDate}" disabled>
            <br>
            <p><fmt:message key="label.description" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('description')" value="description">
            <textarea name="description" class="form-control" placeholder="${film.description}" disabled></textarea>
            <br>
            <p><fmt:message key="label.rating" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('rating')" value="rating">
            <input type="text" name="rating" class="form-control" placeholder="${film.rating}" disabled>
            <br>
            <p><fmt:message key="label.poster" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('poster')" value="poster">
            <input type="file" name="poster" id="poster" disabled>
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

