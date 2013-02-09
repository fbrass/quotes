<%@tag description="Show login status with logout link" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="login-status">
    <%--TODO username is hard coded --%>
    Welcome: <sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username"/></sec:authorize>
    |  <a href="/j_spring_security_logout">Logout</a>
</div>

