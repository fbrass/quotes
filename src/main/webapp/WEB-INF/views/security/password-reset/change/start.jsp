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
    <title>Password Reset Step 2</title>
</head>
<body onload="document.getElementById('password').focus()">
<h1>Password Reset Step 2</h1>

<p>Instructions: TODO</p>

<form:form name="passwordReset" commandName="passwordReset" action="/s/pwr/c">
    <%--<form:errors path="*" cssClass="validation-errors" element="div"/>--%>
    <table>
        <tr>
            <td colspan="2">
                <form:hidden path="email"/>
                <form:hidden path="passwordResetToken"/>
            </td>
        </tr>
        <tr>
            <td>New password:</td>
            <td>
                <form:password path="password" id="password"/>
                <form:errors path="password" cssClass="validation-msg"/>
            </td>
        </tr>
        <tr>
            <td>New password repeat:</td>
            <td>
                <form:password path="password2"/>
                <form:errors path="password2" cssClass="validation-msg"/>
                <form:errors path="" cssClass="validation-msg"/> <%-- show FieldsMatch validation  --%>
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
