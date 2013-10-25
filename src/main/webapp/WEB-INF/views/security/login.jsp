<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="appname"><spring:message code="app.name"/></c:set>
<c:set var="title"><spring:message code='login.title'/></c:set>
<c:set var="formEmail"><spring:message code="form.email"/></c:set>
<c:set var="formPassword"><spring:message code="form.password"/></c:set>

<t:securitypage title="${title}">
    <jsp:attribute name="afterScripts">
        <script type="text/javascript">
            $('#j_username').focus();
        </script>
    </jsp:attribute>
    <jsp:body>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <spring:message code="login.errorMessage"/> ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>

        <%--suppress XmlPathReference --%>
        <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST" role="form" class="form-signin">
            <fieldset>
                <legend><span class="brand">${appname}</span> ${title}</legend>
                <div class="form-group">
                    <label for="j_username">${formEmail}</label>
                    <input class="form-control" type='email' placeholder="${formEmail}" id="j_username" name='j_username'
                        value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
                </div>
                <div class="form-group">
                    <label for="j_password">${formPassword}&nbsp;<span class="forget">(<a
                        href="<c:url value="/s/pwr"/>"><spring:message code="login.passwordForget"/></a>)</span></label>
                    <input class="form-control" type='password' placeholder="${formPassword}" id="j_password" name='j_password'/>
                </div>
                <div class="form-group">
                    <label for="_spring_security_remember_me" class="checkbox">
                        <input type="checkbox" id="_spring_security_remember_me"
                            name="_spring_security_remember_me"><spring:message code="login.rememberMe"/>
                    </label>
                    <button type="submit" class="btn btn-primary"><spring:message code="login.submit"/></button>
                        &nbsp; or <a href="<c:url value="/s/r"/>"><spring:message code="registration.title"/></a> in a
                        minute
                </div>
            </fieldset>
        </form>
    </jsp:body>
</t:securitypage>
