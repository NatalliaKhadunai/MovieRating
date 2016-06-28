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
    <script src="js/validateSignUp.js"></script>
</head>

<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <form class="col-md-3" action="/RatingServlet?requestType=Registration" method="post" onsubmit="return validate(this);">
            <h2 class="form-signin-heading" style="text-align: center;"><fmt:message key="label.pleaseEnterInfoForSignUp" bundle="${rb}"/></h2>
            <br>
            <label><fmt:message key="label.login" bundle="${rb}"/>:</label>
            <c:choose>
                <c:when test="${requestScope.loginExists eq true}">
                    <label style="color: #c9302c;"><fmt:message key="label.suchLoginAlreadyExists" bundle="${rb}"/></label>
                    <input type="text" name="login" class="form-control" style="border: 1px solid #c9302c;" placeholder="<fmt:message key="label.login" bundle="${rb}"/>" required autofocus>
                </c:when>
                <c:when test="${requestScope.loginExists eq null}">
                    <input type="text" name="login" class="form-control" placeholder="<fmt:message key="label.login" bundle="${rb}"/>" required autofocus>
                </c:when>
            </c:choose>
            <br>
            <label><fmt:message key="label.email" bundle="${rb}"/>:</label>
            <input type="email" name="email" class="form-control" placeholder="<fmt:message key="label.email" bundle="${rb}"/>" required autofocus>
            <br>
            <label><fmt:message key="label.password" bundle="${rb}"/>:</label>
            <input type="password" name="password" class="form-control" placeholder="<fmt:message key="label.password" bundle="${rb}"/>" required>
            <br>
            <input type="submit" name="submit" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="button.signUp" bundle="${rb}"/>">
        </form>
    </div>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div> <!-- /container -->

<script src="js/jquery-2.2.3.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-2.2.3.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>

</body></html>