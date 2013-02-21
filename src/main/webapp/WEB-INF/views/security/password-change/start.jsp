<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:genericpage>
    <jsp:attribute name="title">Change password</jsp:attribute>
    <jsp:body>
        <div class="row">
            <div class="span6">
                <h2>Change Password</h2>
                <form:form name="passwordChange" commandName="passwordChange" action="/s/pwc">
                    <%--<form:errors path="*" cssClass="validation-errors" element="div"/>--%>
                    <fieldset>
                        <legend>Please enter Your data</legend>
                        <div class="control-group">
                            <label for="currentPassword" class="control-label">Current password</label>
                            <div class="controls">
                                <form:password path="currentPassword" id="currentPassword"/>
                                <form:errors path="currentPassword" element="p" id="currentPassword-errors" cssClass="help-block"/>
                            </div>
                        </div>
                        <div class="control-group error">
                            <label for="password" class="control-label">New password</label>
                            <div class="controls">
                                <form:password path="password" id="password"/>
                                <form:errors path="password" element="p" cssClass="help-block"/>
                            </div>
                        </div>
                        <div class="control-group success">
                            <label for="password2" class="control-label">Password repeat</label>
                            <div class="controls">
                                <form:password path="password2" id="password2"/>
                                <form:errors path="password2" element="p" cssClass="help-block"/>
                                <form:errors path="" element="p" cssClass="help-block"/> <%-- show FieldsMatch validation  --%>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <button type="submit" class="btn btn-primary">Change Password</button>
                            </div>
                        </div>
                    </fieldset>
                </form:form>
            </div>
            <div class="span6">
                <h3>About good passwords</h3>
                <p>
                    Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.
                </p>
                <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?
                </p>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
