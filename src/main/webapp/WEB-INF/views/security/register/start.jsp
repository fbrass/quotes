<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="appname"><spring:message code="app.name"/></c:set>
<c:set var="title"><spring:message code='registration.title'/></c:set>
<c:set var="formEmail"><spring:message code="form.email"/></c:set>
<c:set var="formPassword"><spring:message code="form.password"/></c:set>
<c:set var="formPassword2"><spring:message code="form.password2"/></c:set>

<t:securitypage title="${title}">
    <jsp:attribute name="afterScripts">
        <script type="text/javascript">
            $('#email').focus();
        </script>
    </jsp:attribute>
    <jsp:body>
        <form:form name="registration" commandName="registration" action="${pageContext.request.contextPath}/s/r" role="form" cssClass="form-signin">
            <fieldset>
                <legend><span class="brand">${appname}</span> ${title}</legend>
                <div class="form-group">
                    <label for="email" class="control-label">${formEmail}</label>
                    <form:input path="email" id="email" type="email" placeholder="${formEmail}" cssClass="form-control"/>
                    <spring:bind path="email">
                        <c:if test="${status.error}">
                            <span class="help-block"><c:out value="${status.errorMessages[0]}"/></span>
                        </c:if>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <label for="password" class="control-label">${formPassword}</label>
                    <form:password path="password" id="password" placeholder="${formPassword}" cssClass="form-control"/>
                    <spring:bind path="password">
                        <c:if test="${status.error}">
                            <span class="help-block"><c:out value="${status.errorMessages[0]}"/></span>
                        </c:if>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <label for="password2" class="control-label">${formPassword2}</label>
                    <form:password path="password2" id="password2" placeholder="${formPassword2}" cssClass="form-control"/>
                    <form:errors path="password2" element="span" cssClass="help-block"/>
                    <form:errors path="" element="span" cssClass="help-block"/> <%-- show FieldsMatch validation  --%>
                </div>
                <br/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary"><spring:message code="registration.submit"/></button>
                    &nbsp; or <a href="<c:url value="/"/>"><spring:message code="login.title"/></a>
                </div>
            </fieldset>
        </form:form>
    </jsp:body>
</t:securitypage>
