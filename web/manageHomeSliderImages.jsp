<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/validateAddFrame.js"></script>
</head>

<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <p><fmt:message key="label.pressOnImageToRemoveIt" bundle="${rb}"/>:</p>
        <c:forEach items="${homeSliderImages}" var="image">
            <a href="/RatingServlet?requestType=removeHomeSliderImage&fileName=${image}">
                <img class="col-md-3" src="img/frame/${image}">
            </a>
        </c:forEach>
    </div>

    <div class="row">
        <p><fmt:message key="label.orYouCanAddAnotherOne" bundle="${rb}"/>:</p>
        <form action="/RatingServlet?requestType=addHomeSliderImage" method="post" enctype="multipart/form-data" onsubmit="return validate(this);">
            <p><fmt:message key="label.enterVideoProductName" bundle="${rb}"/>: </p>
            <br>
            <input type="text" name="videoProductName" placeholder="<fmt:message key="label.enterVideoProductName" bundle="${rb}"/>" required>
            <br>
            <p><fmt:message key="label.chooseFrame" bundle="${rb}"/>: </p>
            <br>
            <input type="file" name="frame" id="frame" required>
            <br>
            <input type="submit" name="submit" class="btn btn-primary" value="<fmt:message key="button.add" bundle="${rb}"/>">
        </form>
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