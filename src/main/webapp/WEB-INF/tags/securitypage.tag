<%@ tag description="Security page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="afterScripts" fragment="true" required="false" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <title><spring:message code="app.name"/> - ${title}</title>
</head>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <ul class="nav">
                <li class="active">
                    <a class="brand" href="/"><spring:message code="app.name"/></a>
                </li>
                <li><a href="#">Link1</a></li>
                <li><a href="#">Link2</a></li>
                <li><a href="#">Link3</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container">
    <jsp:doBody/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <jsp:invoke fragment="afterScripts"/>
</div>
</body>
</html>
