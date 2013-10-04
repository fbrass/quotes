<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin/Users</title>
</head>
<body>
    <h1>Admin/Users</h1>

    <p>User administration:</p>

    <table>
        <tr>
            <th>E-Mail</th>
            <th>Enabled</th>
            <th>Has registration token</th>
            <th>Registered since</th>
            <th>Has password reset token</th>
            <th>Password reset requested at</th>
        </tr>
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
    </table>

</body>
</html>
