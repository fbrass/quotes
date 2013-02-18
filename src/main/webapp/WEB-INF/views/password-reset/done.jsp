<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Password reset instructions sent</title>
</head>
<body>
<h1>Password reset instructions sent</h1>

<p>
    <spring:message code="passwordReset.successMessage" arguments="${email}"/>
</p>
</body>
</html>
