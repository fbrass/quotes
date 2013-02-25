<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="title"><spring:message code="passwordChange.title"/></c:set>

<t:genericpage title="${title}">
    <jsp:attribute name="javascript">
        <script type="text/javascript">
            $("#currentPassword").focus();
            $(".help-inline").each(function(i) { $(this).closest(".control-group").addClass("error") });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="span8">
                <h2>${title}</h2>
                <form:form name="passwordChange" commandName="passwordChange" action="/s/pwc">
                    <fieldset>
                        <legend><spring:message code="passwordChange.legend"/></legend>
                        <div class="control-group">
                            <label for="currentPassword" class="control-label"><spring:message code="passwordChange.currentPassword"/></label>
                            <div class="controls">
                                <form:password path="currentPassword" id="currentPassword"/>
                                <spring:bind path="currentPassword">
                                <c:if test="${status.error}">
                                    <span class="help-inline">
                                        <c:out value="${status.errorMessages[0]}"/>
                                    </span>
                                </c:if>
                                </spring:bind>
                            </div>
                        </div>
                        <div class="control-group">
                            <label for="password" class="control-label"><spring:message code="passwordChange.newPassword"/></label>
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
                                <form:errors path="" element="span" cssClass="help-inline"/> <%-- show FieldsMatch validation  --%>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <button type="submit" class="btn btn-primary"><spring:message code="passwordChange.submit"/></button>
                            </div>
                        </div>
                    </fieldset>
                </form:form>
            </div>
            <div class="span4">
                <h3><spring:message code="passwordChange.goodPasswords.title"/></h3>
                <spring:message code="passwordChange.goodPasswords.text"/>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
