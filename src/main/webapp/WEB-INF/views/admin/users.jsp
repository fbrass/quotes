<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:genericpage>
    <jsp:attribute name="title">Administration</jsp:attribute>

    <jsp:body>
        <h4>User list</h4>
        <table class="table table-striped table-bordered table-responsive table-condensed">
            <thead>
            <tr>
                <th>E-Mail</th>
                <th>Enabled</th>
                <th>Registration<br/>token</th>
                <th>Registered<br/>since</th>
                <th>Password<br/>reset token</th>
                <th>Password reset<br/>requested</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.PK}</td>
                    <td>${u.enabled}</td>
                    <td>${u.hasRegistrationToken}</td>
                    <td>${u.registeredSince}</td>
                    <td>${u.hasPasswordResetToken}</td>
                    <td>${u.passwordResetRequestedAt}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:genericpage>
