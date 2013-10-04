<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:genericpage>
    <jsp:attribute name="title">Administration</jsp:attribute>

    <jsp:body>
        <p>Here you find various administrative tasks:</p>
        <ul>
            <li><a href="users">User administration</a></li>
        </ul>
    </jsp:body>
</t:genericpage>
