<c:choose>
    <c:when test="${sessionScope.locale eq 'EN'}">
        <fmt:setLocale value="en_US" scope="session"/>
    </c:when>
    <c:when test="${sessionScope.locale eq 'DE'}">
        <fmt:setLocale value="de_DE" scope="session"/>
    </c:when>
    <c:when test="${sessionScope.locale eq 'RU'}">
        <fmt:setLocale value="ru_RU" scope="session"/>
    </c:when>
</c:choose>
<fmt:setBundle basename="page_content" var="rb"/>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/RatingServlet"><img src="css/img/Logo.png"> </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="/RatingServlet">
                        <fmt:message key="button.home" bundle="${rb}"></fmt:message>
                    </a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <fmt:message key="button.language" bundle="${rb}"></fmt:message>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/RatingServlet?requestType=setLocale&lang=EN">
                                <fmt:message key="button.english" bundle="${rb}"></fmt:message>
                            </a>
                        </li>
                        <li>
                            <a href="/RatingServlet?requestType=setLocale&lang=DE">
                                 <fmt:message key="button.german" bundle="${rb}"></fmt:message>
                            </a>
                        </li>
                        <li>
                            <a href="/RatingServlet?requestType=setLocale&lang=RU">
                                <fmt:message key="button.russian" bundle="${rb}"></fmt:message>
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-left" role="search" action="/RatingServlet?" id="searchForm" name="searchForm">
                <div class="input-group">
                    <input type="hidden" name="requestType" value="search">
                    <input type="text" class="form-control" id="inputGroupSuccess3" aria-describedby="inputGroupSuccess3Status" name="searchText" placeholder="<fmt:message key="label.search" bundle="${rb}"/>">
                    <a class="input-group-addon" href="#" onclick="$(this).closest('form').submit(); return false;">
                        <span class="glyphicon glyphicon-search"></span>
                    </a>
                </div>
            </form>
            <c:choose>
                <c:when test="${sessionScope.isLoggedIn eq false}">
                    <form class="navbar-form navbar-right" action="/RatingServlet?requestType=Login" method="post">
                        <div class="form-group">
                            <input type="text" placeholder="<fmt:message key="label.login" bundle="${rb}"/>" class="form-control" name="login" required>
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="<fmt:message key="label.password" bundle="${rb}"/>" class="form-control" name="password" required>
                        </div>
                        <input type="submit" class="btn btn-success" value="<fmt:message key="button.login" bundle="${rb}"></fmt:message>">
                        <a type="button" class="btn btn-primary" href="/signUp.jsp">
                            <fmt:message key="button.signUp" bundle="${rb}"/>
                        </a>
                    </form>
                </c:when>
                <c:when test="${sessionScope.isLoggedIn eq true}">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                ${sessionScope.loggedUser.login}
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="/loggedUser.jsp">
                                        <fmt:message key="button.myPage" bundle="${rb}"/>
                                    </a>
                                </li>
                                <li>
                                    <a href="changePassword.jsp">
                                        <fmt:message key="button.changePassword" bundle="${rb}"/>
                                    </a>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <a href="/RatingServlet?requestType=exit">
                                        <fmt:message key="button.exit" bundle="${rb}"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </c:when>
            </c:choose>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>