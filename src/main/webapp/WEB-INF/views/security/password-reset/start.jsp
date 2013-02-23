<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title"><spring:message code='passwordReset.title'/></c:set>

<t:securitypage title="${title}">
    <jsp:attribute name="afterScripts">
        <script type="text/javascript">
            $('#email').focus();
            $(".help-inline").each(function (i) { $(this).closest(".control-group").addClass("error") });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>${title}</h2>

        <form:form name="passwordResetAttempt" commandName="passwordResetAttempt" action="/s/pwr" class="form-horizontal">
            <fieldset>
                <legend><spring:message code='passwordReset.legend'/></legend>
                <div class="control-group">
                    <label for="email" class="control-label"><spring:message code="form.email"/></label>

                    <div class="controls">
                        <form:password path="email" id="email"/>
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
                    <div class="controls">
                        <button type="submit" class="btn btn-primary">
                            <spring:message code="passwordReset.submit"/>
                        </button>
                        &nbsp; or <a href="<c:url value="/"/>"><spring:message code="login.title"/></a>
                    </div>
                </div>
            </fieldset>
        </form:form>
    </jsp:body>
</t:securitypage>
