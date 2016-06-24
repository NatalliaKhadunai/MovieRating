<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="css/img/icon-transparent.png">
    <title>Movie Rating</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/videoProductPage.css" rel="stylesheet">
    <script>
        var lastModified;
        var mark = -1;
        var requestForComment;
        var requestForMark;
        function changeColorsOver(callingElement) {
            if (mark == -1 || isNaN(mark)) {
                for (var i = 1; i <= 10; i++) {
                    var element = document.getElementById(i);
                    element.style.color = "#C9C9C9";
                }
                for (var i = 1; i <= parseInt(callingElement.id); i++) {
                    var element = document.getElementById(i);
                    element.style.color = "#FFD23F";
                }
            }
        }

        function putMark(element) {
            var isLoggedIn = '<%=session.getAttribute("isLoggedIn")%>';
            if (isLoggedIn == 'false') {
                alert("You're not logged in!");
                return;
            }
            if (${sessionScope.loggedUser.statusName eq 'BAN'}) {
                alert("You're baned!");
                return;
            }
            if (mark != -1 && !isNaN(mark)) {
                alert("You've already put mark for this film!");
                return;
            }
            else {
                var request;
                var userLogin = '${sessionScope.loggedUser.login}';
                var filmName = '${film.name}';
                var v = element.id;
                var url = "/RatingServlet?requestType=putFilmMark&userLogin=" + userLogin + "&filmName=" + filmName + "&mark=" + v;
                mark = parseInt(v);
                if (window.XMLHttpRequest) {
                    request = new XMLHttpRequest();
                }
                else if (window.ActiveXObject) {
                    request = new ActiveXObject("Microsoft.XMLHTTP");
                }

                try {
                    request.onreadystatechange = getInfo;
                    request.open("GET", url, true);
                    request.send();
                }
                catch (e) {
                    alert("Unable to connect to server");
                }
            }
        }

        function putComment() {
            var isLoggedIn = '<%=session.getAttribute("isLoggedIn")%>';
            if (isLoggedIn == 'false') {
                alert("You're not logged in!");
                return;
            }
            if (${sessionScope.loggedUser.statusName eq 'BAN'}) {
                alert("You're baned!");
                return;
            }
            var content = document.getElementById("commentContent").value;
            if (content.replace("\s") == '') {
                alert("Comment should contain text!");
                return;
            }
            else {
                var userLogin = '${sessionScope.loggedUser.login}';
                var filmName = '${film.name}';
                var dateNow = Date.now();
                lastModified = dateNow;
                document.getElementById("commentContent").value = "";
                var url = "/RatingServlet?requestType=putFilmComment&userLogin=" + userLogin + "&filmName=" +
                        filmName + "&date=" + dateNow + "&content=" + content + "&lastModified=" + lastModified;
                if (window.XMLHttpRequest) {
                    requestForComment = new XMLHttpRequest();
                }
                else if (window.ActiveXObject) {
                    requestForComment = new ActiveXObject("Microsoft.XMLHTTP");
                }

                try {
                    requestForComment.onreadystatechange = getInfo;
                    requestForComment.open("GET", url, true);
                    requestForComment.send();
                }
                catch (e) {
                    alert("Unable to connect to server");
                }
            }
        }

        function getInfo(){
            if(requestForComment.readyState==4 && requestForComment.status == 200){
                var dlList = document.getElementById("commentsDL");
                var comments = requestForComment.responseXML.getElementsByTagName("comments")[0];
                var items = comments.getElementsByTagName("comment");
                for (var i=0;i<items.length;i++) {
                    var divNode = document.createElement("div");
                    divNode.className = "row";
                    divNode.style.marginTop = "20px";
                    var nodeDT = document.createElement("dt");
                    var nodeA = document.createElement("a");
                    var nodeAText = document.createTextNode(items[i].getElementsByTagName("userLogin")[0].firstChild.nodeValue);
                    nodeA.setAttribute("href","/RatingServlet?requestType=userPage&userLogin=" + items[i].getElementsByTagName("userLogin")[0].firstChild.nodeValue);
                    nodeA.appendChild(nodeAText);
                    nodeDT.appendChild(nodeA);
                    nodeDT.appendChild(document.createTextNode(" on " + items[i].getElementsByTagName("date")[0].firstChild.nodeValue));
                    var nodeDD = document.createElement("dd");
                    nodeDD.innerHTML = items[i].getElementsByTagName("content")[0].firstChild.nodeValue;
                    divNode.appendChild(nodeDT);
                    divNode.appendChild(nodeDD);
                    dlList.appendChild(divNode);
                }
            }
        }

        function initialization() {
            var element = document.getElementById("1");
            for (var i=2;i<=10;i++) {
                var copyElement = element.cloneNode();
                copyElement.id = i;
                var divElement = document.getElementById("ratingContainer");
                divElement.appendChild(copyElement);
            }
            lastModified = Date.now();
            checkMarkExists();
        }

        function checkMarkExists() {
            var isLoggedIn = '<%=session.getAttribute("isLoggedIn")%>';
            if (isLoggedIn == 'true' && !${sessionScope.loggedUser.statusName eq 'BAN'}) {
                var userLogin = '${sessionScope.loggedUser.login}';
                var filmName = '${film.name}';
                var url = "/RatingServlet?requestType=checkFilmMarkExists&userLogin=" + userLogin + "&filmName=" +
                        filmName;
                if (window.XMLHttpRequest) {
                    requestForMark = new XMLHttpRequest();
                }
                else if (window.ActiveXObject) {
                    requestForMark = new ActiveXObject("Microsoft.XMLHTTP");
                }

                try {
                    requestForMark.onreadystatechange = checkMarkExistsResponse;
                    requestForMark.open("GET", url, true);
                    requestForMark.send();
                }
                catch (e) {
                    alert("Unable to connect to server");
                }
            }
        }

        function checkMarkExistsResponse() {
            if(requestForMark.readyState==4 && requestForMark.status == 200){
                mark = parseInt(requestForMark.responseText);
                if (mark != -1 && !isNaN(mark)) {
                    for (var i=1;i<=10;i++) {
                        var element = document.getElementById(i);
                        element.style.color = "#C9C9C9";
                    }
                    for (var i=1;i<=mark;i++) {
                        var element = document.getElementById(i);
                        element.style.color = "#FFD23F";
                    }
                }
            }
        }
    </script>
</head>

<body onload="initialization()">

<%@ include file="navbar.jsp"%>

<div class="container">

    <div class="row">
        <img src="img/video product/${film.posterFileName}" class="col-md-3" style="position: absolute;">
        <h1 class="col-md-6 col-md-offset-4">${film.name}</h1>
        <p class="col-md-7 col-md-offset-4">${film.description}</p>
        <h4 class="col-md-9 col-md-offset-4"><fmt:message key="label.releaseDate" bundle="${rb}"/>:
            <fmt:formatDate value="${film.releaseDate}"/>
        </h4>
        <h3 class="col-md-1 col-md-offset-4"><fmt:message key="label.rating" bundle="${rb}"/>:</h3>
        <h2 class="col-md-1 rating">${film.rating}</h2></p>
        <h3 class="col-md-8 col-md-offset-4"><fmt:message key="label.rateThis" bundle="${rb}"/>:</h3>
        <div class="col-md-8 col-md-offset-4" id="ratingContainer">
            <span class="glyphicon glyphicon-star" aria-hidden="true" style="font-size: 3rem;" onmouseover="changeColorsOver(this)" onclick="putMark(this)" id="1"></span>
        </div>
    </div>

    <div class="row" style="margin-top: 85px;">
        <h3><fmt:message key="label.leaveYourComment" bundle="${rb}"/></h3>
        <textarea class="form-control" class="col-md-4" name="commentContent" id="commentContent"></textarea>
        <br>
        <button class="btn btn-primary" onclick="putComment()">
            <fmt:message key="button.addComment" bundle="${rb}"/>
        </button>
    </div>

    <h2 style="margin-top: 40px;"><fmt:message key="label.comments" bundle="${rb}"/>:</h2>
    <dl id="commentsDL">
        <c:forEach items="${commentList}" var="comment" varStatus="status">
            <div class="row" style="margin-top: 20px;">
                <dt class="commentInfo">
                    <a href="/RatingServlet?requestType=userPage&userLogin=${comment.userLogin}">${comment.userLogin}</a>
                    on
                    <fmt:formatDate value="${comment.date}" type="date"/>
                    <fmt:formatDate value="${comment.date}" type="time" timeStyle="short"/>
                </dt>
                <dd>${comment.content}</dd>
            </div>
        </c:forEach>
    </dl>
    <hr>

    <footer>
        <p>© 2016 Khadunai Natallia, Final Proj.</p>
    </footer>
</div><!-- /.container -->

<script src="js/jquery-2.2.3.js"></script>
<script>window.jQuery || document.write('<script src="js/jquery-2.2.3.js"><\/script>')</script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>