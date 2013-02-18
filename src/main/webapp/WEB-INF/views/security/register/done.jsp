<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thanks for registering</title>
</head>
<body>
    <h1>Thanks for registering</h1>

    <p>
        <spring:message code="registration.successMessage" arguments="${email}"/>
    </p>
</body>
</html>
