<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Generic page template" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/main.css" type="text/css"/>
    <title>${title}</title>
</head>
<body>
    <div id="header">
        <h1>${title}</h1>
    </div>

    <div id="content">
        <jsp:doBody/>
    </div>

    <div id="footer">
        <c:choose>
            <c:when test="${cart.size == 1}">
                <p>
                    Your shopping cart contains <strong>one</strong> item.
                    <a href="/cart">View Cart&hellip;</a>
                </p>
            </c:when>
            <c:when test="${cart.size > 1}">
                <p>
                    Your shopping cart contains <strong><c:out value="${cat.size}"/></strong> items.
                    <a href="/cart">View Cart&hellip;</a>
                </p>
            </c:when>
        </c:choose>
    </div>
</body>
</html>
