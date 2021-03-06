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
        <div class="rounded-image" style="background-image: url('img/user/${user.profilePhoto}');">
        </div>
    </div>
    <div class="row">
        <div class="center-block" style="width: 300px;">
            <h1>${user.login}</h1>
            <h4>
                <fmt:message key="label.fullName" bundle="${rb}"/>: ${user.name}
            </h4>
            <h4>
                <fmt:message key="label.email" bundle="${rb}"/>: ${user.email}
            </h4>
            <h4>
                <fmt:message key="label.sex" bundle="${rb}"/>: ${user.sex}
            </h4>
            <h4>
                <fmt:message key="label.status" bundle="${rb}"/>
                <ct:statusTag status="${user.statusName}"></ct:statusTag>
            </h4>
            <h4>
                <fmt:message key="label.statusCoefficient" bundle="${rb}"/>: ${user.statusCoefficient}
            </h4>
        </div>
    </div>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div><!-- /.container -->

<script src="js/jquery-2.2.3.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-2.2.3.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>


</body></html>