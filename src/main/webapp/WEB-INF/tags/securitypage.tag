<%@ tag description="Security page template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="head" fragment="true" required="false" %>
<%@ attribute name="afterScripts" fragment="true" required="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
    <title><spring:message code="app.name"/> - ${title}</title>
    <jsp:invoke fragment="head"/>
</head>
<body>
<div class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><spring:message code="app.name"/>&nbsp;&trade;</a>
        </div>

        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href=".">Link 1</a></li>
                <li><a href=".">Link 2</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
<div class="container">
    <jsp:doBody/>
    <script src="${pageContext.request.contextPath}/js/jquery-2.0.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <jsp:invoke fragment="afterScripts"/>
</div>
</body>
</html>
