<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Password reset error</title>
</head>
<body>
    <h1>Password reset error</h1>

    <%--@elvariable id="errorMessageKey" type="java.lang.String"--%>
    <spring:message code="${errorMessageKey}"/>

</body>
</html>
