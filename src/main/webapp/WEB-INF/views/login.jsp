<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style type="text/css">
        .errorblock {
            color: #ff0000;
            background-color: #ffEEEE;
            border: 3px solid #ff0000;
            padding: 8px;
            margin: 16px;
        }
    </style>
</head>
<body onload="document.f.j_username.focus();">

    <h1>Login</h1>

    <c:if test="${not empty error}">
        <div class="errorblock">
            Your login attempt was not successful, try again.<br /> Caused :
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>

    <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='j_password'></td>
            </tr>
            <tr>
                <td><input type="checkbox" name="_spring_security_remember_me"></td>
                <td>Don't ask for my password for two weeks</td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"></td>
            </tr>
            <tr>
                <td colspan='2'><input name="reset" type="reset"></td>
            </tr>
        </table>
    </form>

    <p style="color: #ff69b4">
        Not a member? You may <a href="<c:url value="/register"/>">Signup in one minute</a>!
    </p>

</body>
</html>
