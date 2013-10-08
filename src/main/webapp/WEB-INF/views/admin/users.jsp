<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:genericpage>
    <jsp:attribute name="title">Administration</jsp:attribute>

    <jsp:attribute name="javascript">
    <%--<script type="text/javascript">--%>
        <%--function enableDisableUser(contextPath, enableFlag, user) {--%>
            <%--var params = { userId: user };--%>
            <%--jQuery.ajax(contextPath + '/admin/' + enableFlat ? 'enable' : 'disable' + '-user/', { type: 'POST', data: params,--%>
                <%--error: function () { alert("Error") },--%>
                <%--success: function () { alert("User disabled") }--%>
            <%--});--%>
        <%--}--%>
        <%--function passwordReset(contextPath, user) {--%>
            <%--var params = { userId: user };--%>
            <%--jQuery.ajax(contextPath + '/admin/password-reset-user', { type: 'POST', data: params,--%>
                <%--error: function () { alert("Error") },--%>
                <%--success: function () { alert("Password reset sent") }--%>
            <%--});--%>
        <%--}--%>
    <%--</script>--%>
    </jsp:attribute>

    <jsp:body>
        <h4>User list</h4>
        <table class="table table-striped table-responsive table-condensed">
            <thead>
            <tr>
                <th>E-Mail</th>
                <th>Enabled</th>
                <th>Registration<br/>token</th>
                <th>Registered<br/>since</th>
                <th>Password<br/>reset token</th>
                <th>Password reset<br/>requested</th>
                <th>Actions</th>
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
                    <td>
                        <c:choose>
                        <c:when test="${u.enabled}">
                            <a href="<spring:url value='/admin/disable-user/{userId}'><spring:param name='userId' value='${u.PK}'/></spring:url>" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-remove"></span> Disable</a>
                        </c:when>
                        <c:otherwise>
                            <a href="<spring:url value='/admin/enable-user/{userId}'><spring:param name='userId' value='${u.PK}'/></spring:url>" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-plus"></span> Enable</a>
                        </c:otherwise>
                        </c:choose>
                        <a href="<spring:url value='/admin/password-reset-user/{userId}'><spring:param name='userId' value='${u.PK}'/></spring:url>" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-user"></span> Reset password</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>

</t:genericpage>
