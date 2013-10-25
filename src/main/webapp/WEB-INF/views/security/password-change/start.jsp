<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="appname"><spring:message code="app.name"/></c:set>
<c:set var="title"><spring:message code='passwordChange.title'/></c:set>
<c:set var="currentPassword"><spring:message code="passwordChange.currentPassword"/></c:set>
<c:set var="newPassword"><spring:message code="passwordChange.newPassword"/></c:set>
<c:set var="formPassword2"><spring:message code="form.password2"/></c:set>

<t:genericpage title="${title}">
    <jsp:attribute name="afterScripts">
        <script type="text/javascript">
            $("#currentPassword").focus();
        </script>
    </jsp:attribute>
    <jsp:body>
        <form:form name="passwordChange" commandName="passwordChange" action="${pageContext.request.contextPath}/s/pwc"
                   role="form" class="form-signin">
            <fieldset>
                <legend>${title}</legend>
                <div class="form-group">
                    <label for="currentPassword" class="control-label">${currentPassword}</label>
                    <form:password path="currentPassword" id="currentPassword" placeholder="${currentPassword}"
                                   cssClass="form-control"/>
                    <spring:bind path="currentPassword">
                        <c:if test="${status.error}">
                            <span class="help-block">
                                <c:out value="${status.errorMessages[0]}"/>
                            </span>
                        </c:if>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <label for="password" class="control-label">${newPassword}</label>
                    <form:password path="password" id="password" placeholder="${newPassword}" cssClass="form-control"/>
                    <spring:bind path="password">
                        <c:if test="${status.error}">
                            <span class="help-block">
                                <c:out value="${status.errorMessages[0]}"/>
                            </span>
                        </c:if>
                    </spring:bind>
                </div>
                <div class="form-group">
                    <label for="password2" class="control-label">${formPassword2}</label>
                    <form:password path="password2" id="password2" placeholder="${formPassword2}"
                                   cssClass="form-control"/>
                    <form:errors path="password2" element="span" cssClass="help-block"/>
                    <form:errors path="" element="span" cssClass="help-block"/> <%-- show FieldsMatch validation  --%>
                </div>
                <div class="form-group">
                    <br/>
                    <button type="submit" class="btn btn-primary">
                        <spring:message code="passwordChange.submit"/>
                    </button>
                    &nbsp; or <a href="<c:url value="/"/>">cancel</a>
                </div>
            </fieldset>
        </form:form>
    </jsp:body>
</t:genericpage>
