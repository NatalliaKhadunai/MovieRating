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
    <link href="css/index.css" rel="stylesheet">
</head>

<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <form class="col-md-4" action="/RatingServlet?requestType=changePassword" method="post">
            <h2><fmt:message key="label.specifyOldAndNewPasswords" bundle="${rb}"/></h2>
            <br>
            <label><fmt:message key="label.oldPassword" bundle="${rb}"/>:</label>
            <c:choose>
                <c:when test="${requestScope.incorrectPassword eq true}">
                    <label style="color: #c9302c;">
                        <fmt:message key="label.incorrectPassword" bundle="${rb}"/>
                    </label>
                    <input type="password" name="oldPassword" class="form-control" style="border: 1px solid #c9302c;" placeholder="<fmt:message key="label.oldPassword" bundle="${rb}"/>" required autofocus>
                </c:when>
                <c:when test="${requestScope.incorrectPassword eq null}">
                    <input type="password" name="oldPassword" class="form-control" placeholder="<fmt:message key="label.oldPassword" bundle="${rb}"/>" required>
                </c:when>
            </c:choose>
            <br>
            <label><fmt:message key="label.newPassword" bundle="${rb}"/>:</label>
            <input type="password" name="newPassword" class="form-control" placeholder="<fmt:message key="label.newPassword" bundle="${rb}"/>" required>
            <br>
            <input type="submit" name="confirmPassword" class="btn btn-lg btn-primary btn-block" value="<fmt:message key="button.changePassword" bundle="${rb}"/>">
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

</body>
</html>
