<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="title"><spring:message code='login.title'/></c:set>

<t:securitypage title="${title}">
    <jsp:attribute name="afterScripts">
        <script type="text/javascript">
            $('#j_username').focus();
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>${title}</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-block alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <spring:message code="login.errorMessage"/> ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>

        <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST" class="form-horizontal">
            <fieldset>
                <div class="control-group">
                    <label for="j_username" class="control-label"><spring:message code="form.email"/></label>

                    <div class="controls">
                        <input type='text' id="j_username" name='j_username'
                               value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="j_password" class="control-label"><spring:message code="form.password"/></label>

                    <div class="controls">
                        <input type='password' id="j_password" name='j_password'/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <label for="_spring_security_remember_me" class="checkbox" style="color: #808080">
                            <input type="checkbox" id="_spring_security_remember_me"
                                   name="_spring_security_remember_me"><spring:message code="login.rememberMe"/>
                            </input>
                        </label>
                        <button type="submit" class="btn btn-primary"><spring:message code="login.submit"/></button>
                        &nbsp; or <a href="<c:url value="/s/r"/>"><spring:message code="registration.title"/></a> in a minute
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls" style="color: #808080">
                        <a href="<c:url value="/s/pwr"/>"><spring:message code="login.passwordForget"/></a>?
                    </div>
                </div>
            </fieldset>
        </form>
    </jsp:body>
</t:securitypage>
