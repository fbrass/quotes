<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Activation Success</title>
</head>
<body>
    <h1>Activation Success</h1>

    <p>
        <spring:message code="registration.activation.successMessage" arguments="${email}"/>
        <br/><a href="/login">Login now</a>.
    </p>
</body>
</html>
