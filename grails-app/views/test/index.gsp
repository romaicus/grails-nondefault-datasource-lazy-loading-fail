<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="main"/>
        <title></title>
    </head>

    <body>

        Authors Total Count: ${authors.totalCount}

        <table>

            <g:each in="${authors}" var="author">
                <tr>
                    <td>
                        ${author.name}
                    </td>
                    <td>
                        <table>
                        <g:each in="${author.books}" var="book">
                            <tr>
                                <td>
                                    ${book.name}
                                </td>
                            </tr>
                        </g:each>
                        </table>
                    </td>
                </tr>
            </g:each>

        </table>
    </body>

</html>