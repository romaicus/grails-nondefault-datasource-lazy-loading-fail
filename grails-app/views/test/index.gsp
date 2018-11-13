<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="main"/>
        <title></title>
    </head>

    <body>
        <g:each in="${authors}" var="author">
            ${author.name} <hr/>

            <g:each in="${author.books}" var="book">
                ${book.name}<br/>
            </g:each>
        </g:each>

    </body>

</html>