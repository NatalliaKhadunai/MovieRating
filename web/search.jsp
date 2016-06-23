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
</head>

<body>

<%@ include file="navbar.jsp"%>

<div class="container">

    <c:choose>
        <c:when test="${requestScope.searchList != null}">
            <h3><fmt:message key="label.results" bundle="${rb}"/>:</h3>
                <c:forEach items="${searchList}" var="searchItem" varStatus="status">
                        <div class="row" style="margin-top: 20px;">
                            <img src="img/video product/${searchItem.posterFileName}" class="col-md-1">
                            <c:choose>
                                <c:when test="${searchItem['class'].simpleName eq 'Film'}">
                                    <a href="/RatingServlet?requestType=filmPage&filmTitle=${searchItem.name}" class="col-md-9">${searchItem.name}</a>
                                </c:when>
                                <c:when test="${searchItem['class'].simpleName eq 'TVSeries'}">
                                    <a href="/RatingServlet?requestType=tvseriesPage&tvseriesTitle=${searchItem.name}" class="col-md-9">${searchItem.name}</a>
                                </c:when>
                            </c:choose>
                            <span class="col-md-9">${searchItem.description}</span>
                        </div>
                </c:forEach>
        </c:when>
    </c:choose>

    <div class="row">
        <div class="btn-group center-block" role="group" style="margin-top: 30px;">
            <c:forEach begin="1" end="${maxPageNo}" var="val">
                <c:choose>
                    <c:when test="${val eq pageNo}">
                        <a class="btn btn-primary" href="/RatingServlet?requestType=search&searchText=${searchText}&pageNo=${val}">${val}</a>
                    </c:when>
                    <c:when test="${val != pageNo}">
                        <a class="btn btn-default" href="/RatingServlet?requestType=search&searchText=${searchText}&pageNo=${val}">${val}</a>
                    </c:when>
                </c:choose>
            </c:forEach>
        </div>
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