<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Friend Requests</title>
</head>
<body>
    <c:forEach items="${requests}" var="r" >
        <div class="request-div">
            <h2>from: ${r.sender.username}</h2>
            <form:form action="/friendRequest/accept/${r.id}">
                <input type="submit" value="Accept Request">
            </form:form>
            <input type="button" value="Reject Request">
        </div>
    </c:forEach>
</body>
</html>