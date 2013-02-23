<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="title"><spring:message code='registration.error.title'/></c:set>

<t:securitypage title="${title}">
    <jsp:body>
        <h2>${title}</h2>

        <div class="alert alert-block alert-error">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <p>
                <spring:message code="${errorMessageKey}"/>
            </p>
        </div>
    </jsp:body>
</t:securitypage>
