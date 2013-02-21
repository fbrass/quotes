<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@tag description="Generic page template" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/main.css" type="text/css"/>
    <title>${title}</title>
</head>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <li class="active">
                    <a class="brand" href="/">${title}</a>
                </li>
                <li><a href="#">Link1</a></li>
                <li><a href="#">Link2</a></li>
                <li><a href="#">Link3</a></li>
            </ul>
            <ul class="nav pull-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username"/></sec:authorize> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="/s/pwc">Change password</a></li>
                        <li><a href="/j_spring_security_logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-search pull-right" style="margin-right:10px">
                <input type="text" class="search-query" placeholder="Search"/>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <jsp:doBody/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</div>
</body>
</html>
