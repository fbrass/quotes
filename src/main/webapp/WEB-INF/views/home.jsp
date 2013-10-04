<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:genericpage>
    <jsp:attribute name="title">Tododont</jsp:attribute>

    <jsp:body>
        <div class="hero-unit">
            <h1>Awesome ${title} 'title' here, rotten junk</h1>

            <p>
                Hi, I'm a .hero-unit. You can present with me super important information.
            </p>

            <p>
                <a class="btn btn-primary btn-large">Get help &raquo;</a>
            </p>
        </div>

        <div class="row">
            <div class="span4">
                <h2>User information</h2>

                <p><t:loginstatus></t:loginstatus></p>

                <p>Your username is ${username} and I have a message for you: ${message}</p>

                <p><a class="btn" href="#">Click meeee &raquo;</a></p>
            </div>

            <div class="span4">
                <h2>Box Number 2</h2>

                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor
                    mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna
                    mollis euismod. Donec sed odio dui. </p>

                <p><a class="btn" href="#">Click meeee &raquo;</a></p>
            </div>

            <div class="span4">
                <h2>Box Number 3</h2>

                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor
                    mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna
                    mollis euismod. Donec sed odio dui. </p>

                <p><a class="btn" href="#">Click meeee &raquo;</a></p>
            </div>
        </div>
    </jsp:body>
</t:genericpage>
