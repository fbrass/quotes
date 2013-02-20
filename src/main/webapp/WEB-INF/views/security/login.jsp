<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
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
<body onload="document.getElementById('j_username').focus()">

    <h1>Login</h1>

    <c:if test="${not empty error}">
    <div class="errorblock">
        Your login attempt was not successful, try again.<br /> Caused :
            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
    </div>
    </c:if>

    <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST" class="form-horizontal">
        <fieldset>
            <div class="control-group">
                <label for="j_username" class="control-label">Email</label>
                <div class="controls">
                    <input type='text' id="j_username" name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
                </div>
            </div>
            <div class="control-group">
                <label for="j_password" class="control-label">Password</label>
                <div class="controls">
                    <input type='password' id="j_password" name='j_password'/>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <label for="_spring_security_remember_me" class="checkbox" style="color: #808080">
                        <input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me">Remember me</input>
                    </label>
                    <button type="submit" class="btn btn-primary">Login</button>
                    &nbsp; or <a href="<c:url value="/s/r"/>">Sign up</a> in a minute
                </div>
            </div>
            <div class="control-group">
                <div class="controls" style="color: #808080">
                    <a href="<c:url value="/s/pwr"/>">Forgot password</a>?
                </div>
            </div>
        </fieldset>
    </form>

    <hr/>

    <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='j_password'/></td>
            </tr>
            <tr>
                <td><input type="checkbox" name="_spring_security_remember_me"/></td>
                <td>Don't ask for my password for two weeks</td>
            </tr>
            <tr>
                <td colspan='2'>
                    <input name="submit" type="submit" value="Login"/>
                    <input name="reset" type="reset" value="Reset"/>
                </td>
            </tr>
        </table>
    </form>

    <p style="color: #ff69b4">
        Not a member? You may <a href="<c:url value="/s/r"/>">SignUp in one minute</a>!
    </p>

    <p>
        Forgot password? <a href="<c:url value="/s/pwr"/>">Reset password</a>!
    </p>

</body>
</html>
