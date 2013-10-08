<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:genericpage>
    <jsp:attribute name="title">Quotes</jsp:attribute>
    <jsp:attribute name="javascript">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/gwtQuotes/gwtQuotes.nocache.js"></script>
    </jsp:attribute>

    <jsp:body>
        <!-- GWT content will be placed here -->
    </jsp:body>
</t:genericpage>
