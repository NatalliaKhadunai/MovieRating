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
                        <th><fmt:message key="label.photo" bundle="${rb}"/></th>
                        <th><fmt:message key="label.login" bundle="${rb}"/></th>
                        <th><fmt:message key="label.email" bundle="${rb}"/></th>
                        <th><fmt:message key="label.fullName" bundle="${rb}"/></th>
                        <th><fmt:message key="label.sex" bundle="${rb}"/></th>
                        <th><fmt:message key="label.status" bundle="${rb}"/></th>
                        <th><fmt:message key="label.statusCoefficient" bundle="${rb}"/></th>
                        <th><fmt:message key="label.actions" bundle="${rb}"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td>
                                <div class="rounded-image" style="background-image: url('img/user/${user.profilePhoto}');">
                                </div>
                            </td>
                            <td><p><a href="/RatingServlet?requestType=userPage&userLogin=${user.login}">${user.login}</a></p></td>
                            <td><p>${user.email}</p></td>
                            <td><p>${user.name}</p></td>
                            <td><p>${user.sex}</p></td>
                            <td><p>${user.statusName}</p></td>
                            <td><p>${user.statusCoefficient}</p></td>
                            <td>
                                <a href="/RatingServlet?requestType=userStatusUp&login=${user.login}" class="btn btn-success" role="button">
                                    <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                                    <fmt:message key="button.statusUp" bundle="${rb}"/>
                                </a>
                                <a href="/RatingServlet?requestType=userStatusDown&login=${user.login}" class="btn btn-warning" role="button">
                                    <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                                    <fmt:message key="button.statusDown" bundle="${rb}"/>
                                </a>
                                <a href="/RatingServlet?requestType=userBan&login=${user.login}" class="btn btn-danger" role="button">
                                    <span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span>
                                    <fmt:message key="button.ban" bundle="${rb}"/>
                                </a>
                                <a href="/RatingServlet?requestType=userRemoveBan&login=${user.login}" class="btn btn-success" role="button">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    <fmt:message key="button.removeBan" bundle="${rb}"/>
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