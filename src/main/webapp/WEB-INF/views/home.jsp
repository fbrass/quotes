<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:genericpage>
    <jsp:attribute name="title">Tododont</jsp:attribute>

    <jsp:body>
        <t:loginstatus></t:loginstatus>

        <div style="border: 1px solid blue">
            Content box
            <ul>
                <li>Username: ${username}</li>
                <li>Message: ${message}</li>
            </ul>
        </div>
    </jsp:body>
</t:genericpage>
