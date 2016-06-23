<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/roundedImageSmall.css" rel="stylesheet">
</head>
<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="label.poster" bundle="${rb}"/></th>
                <th><fmt:message key="label.name" bundle="${rb}"/></th>
                <th><fmt:message key="label.releaseYear" bundle="${rb}"/></th>
                <th><fmt:message key="label.endYear" bundle="${rb}"/></th>
                <th><fmt:message key="label.actions" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tvseriesList}" var="tvseries">
                <tr>
                    <td>
                        <div class="rounded-image" style="background-image: url('img/video product/${tvseries.posterFileName}');">
                        </div>
                    </td>
                    <td><p><a href="/RatingServlet?requestType=tvseriesPage&tvseriesTitle=${tvseries.name}">${tvseries.name}</a></p></td>
                    <td><p>${tvseries.releaseYear}</p></td>
                    <td><p>${tvseries.endYear}</p></td>
                    <td>
                        <a href="/RatingServlet?requestType=tvseriesEditPage&tvseriesName=${tvseries.name}" class="btn btn-success" role="button">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <fmt:message key="button.edit" bundle="${rb}"/>
                        </a>
                        <a href="/RatingServlet?requestType=tvseriesRemove&tvseriesName=${tvseries.name}" class="btn btn-danger" role="button">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            <fmt:message key="button.remove" bundle="${rb}"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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