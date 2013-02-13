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
    <title>Register</title>
</head>
<body onload="document.userRegistration.email.focus();">
    <h1>Register</h1>

    <p>Just enter your name and email, and we'll send you an email to confirm you're whoom you bless you're belonging to. It's just that simple!</p>

    <form:form name="userRegistration" commandName="userRegistration"  action="/register">
        <form:errors path="*" cssClass="validation-errors" element="div" />
        <table>
            <tr>
                <td>Email (never published):</td>
                <td>
                    <form:input path="email"/>
                    <form:errors path="email" cssClass="validation-msg"/>
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <form:password path="password"/>
                    <form:errors path="password" cssClass="validation-msg"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Register"/>
                </td>
            </tr>
        </table>
    </form:form>

    <h2>Good passwords</h2>
    <p>
        must at be at least 10 letters long, contain at least one lower case letter, contain at least one UPPER case letter, contain at least one special letter and no whitespaces
    </p>

</body>
</html>
