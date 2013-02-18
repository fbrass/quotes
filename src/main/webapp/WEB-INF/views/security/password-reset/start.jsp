<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css">
        .validation-errors {
            border: 1px dotted gray;
            margin-bottom: 2em;
        }

        .validation-msg {
            font-weight: bolder;
            border-bottom: 1px dashed red;
        }
    </style>
    <title>Password Reset</title>
</head>
<body onload="document.getElementById('email').focus();">
<h1>Password Reset</h1>

<p>Instructions: TODO</p>

<form:form name="passwordResetAttempt" commandName="passwordResetAttempt" action="/s/pw">
    <form:errors path="*" cssClass="validation-errors" element="div"/>
    <table>
        <tr>
            <td>Email:</td>
            <td>
                <form:input path="email" id="email"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Change"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
