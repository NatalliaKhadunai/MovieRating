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
    <script src="js/validateProfileEdit.js"></script>
    <script src="js/enableDisableElement.js"></script>
</head>
<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <form class="col-md-3" action="/RatingServlet?requestType=editProfile" method="post" enctype="multipart/form-data" onsubmit="return validate(this);">
            <p><fmt:message key="label.email" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('email')" value="email">
            <input type="text" name="email" class="form-control" placeholder="<fmt:message key="label.email" bundle="${rb}"/>" disabled>
            <br>
            <p><fmt:message key="label.fullName" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('fullName')" value="fullName">
            <input type="text" name="fullName" class="form-control" placeholder="<fmt:message key="label.fullName" bundle="${rb}"/>" disabled>
            <br>
            <p><fmt:message key="label.sex" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('sex')" value="sex">
            <select name="sex" disabled>
                <option>F</option>
                <option>M</option>
            </select>
            <br>
            <p><fmt:message key="label.profilePhoto" bundle="${rb}"/>: </p>
            <input type="checkbox" name="field" onclick="enable('profilePhoto')" value="profilePhoto">
            <input type="file" name="profilePhoto" disabled>
            <br>
            <input type="submit" name="submit" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="button.add" bundle="${rb}"/>">
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
