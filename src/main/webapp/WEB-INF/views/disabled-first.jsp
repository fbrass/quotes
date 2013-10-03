<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" language="javascript" src="/first/first.nocache.js"></script>
    <title>Gwt First via Spring MVC</title>
</head>
<body>
    <h1>Gwt First via Spring MVC</h1>

    <table align="center">
        <tr>
            <td colspan="2" style="font-weight:bold;">Please enter some text:</td>
        </tr>
        <tr>
            <td id="inputTextContainer"></td>
            <td id="buttonContainer"></td>
        </tr>
        <tr>
            <td id="outputTextContainer"></td>
        </tr>
        <tr>
            <td colspan="2" style="color:red;" id="errorLabelContainer"></td>
        </tr>
    </table>
</body>
</html>
