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
    <title>Change Password</title>
</head>
<body onload="document.getElementById('currentPassword').focus()">
<h1>Change Password</h1>

<p>Instructions: TODO</p>

<form:form name="passwordChange" commandName="passwordChange" action="/s/pwc">
    <%--<form:errors path="*" cssClass="validation-errors" element="div"/>--%>
    <table>
        <tr>
            <td>Current password:</td>
            <td>
                <form:password path="currentPassword" id="currentPassword"/>
                <form:errors path="currentPassword" cssClass="validation-msg"/>
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
                <input type="submit" value="Change Password"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
