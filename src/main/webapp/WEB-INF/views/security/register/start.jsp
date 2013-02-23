<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title"><spring:message code='registration.title'/></c:set>

<t:securitypage title="${title}">
    <jsp:attribute name="afterScripts">
        <script type="text/javascript">
            $('#email').focus();
            $(".help-inline").each(function (i) { $(this).closest(".control-group").addClass("error") });
        </script>
    </jsp:attribute>
    <jsp:body>
        <form:form name="registration" commandName="registration" action="/s/r" class="form-horizontal">
            <fieldset>
                <legend><spring:message code="registration.title"/></legend>
                <div class="control-group">
                    <label for="email" class="control-label"><spring:message code="form.email"/></label>

                    <div class="controls">
                        <form:input path="email" id="email"/>
                        <spring:bind path="email">
                            <c:if test="${status.error}">
                                    <span class="help-inline">
                                        <c:out value="${status.errorMessages[0]}"/>
                                    </span>
                            </c:if>
                        </spring:bind>
                    </div>
                </div>
                <div class="control-group">
                    <label for="password" class="control-label"><spring:message code="form.password"/></label>

                    <div class="controls">
                        <form:password path="password" id="password"/>
                        <spring:bind path="password">
                            <c:if test="${status.error}">
                                    <span class="help-inline">
                                        <c:out value="${status.errorMessages[0]}"/>
                                    </span>
                            </c:if>
                        </spring:bind>
                    </div>
                </div>
                <div class="control-group">
                    <label for="password2" class="control-label"><spring:message code="form.password2"/></label>

                    <div class="controls">
                        <form:password path="password2" id="password2"/>
                        <form:errors path="password2" element="span" cssClass="help-inline"/>
                        <form:errors path="" element="span"
                                     cssClass="help-inline"/> <%-- show FieldsMatch validation  --%>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <button type="submit" class="btn btn-primary"><spring:message code="registration.submit"/></button>
                        &nbsp; or <a href="<c:url value="/"/>"><spring:message code="login.title"/></a>
                    </div>
                </div>
            </fieldset>
            <p>
                <spring:message code="registration.help"/>
            </p>
        </form:form>
    </jsp:body>
</t:securitypage>
