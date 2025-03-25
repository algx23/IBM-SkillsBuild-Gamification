<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/requests.css">
    <title>Friend Requests</title>
</head>
<body>
    <h1>Your Friend Requests</h1>
    <p>Here you can see your friend requests. Click Accept Request to accept a request, or click Reject Request to decline it</p>
    <c:forEach items="${requests}" var="r" >
        <div class="request-div">
            <h2>from: ${r.sender.username}</h2>
            <form:form action="/friendRequest/accept/${r.id}">
                <input type="submit" value="Accept Request" id="accept-button">
            </form:form>
            <form:form action="/friendRequest/decline/${r.id}">
                <input type="submit" value="Reject Request" id="reject-button">
            </form:form>
        </div>
    </c:forEach>

    <br>
    <form:form action="/${username}/dashboard-page" method="get">
        <input type="submit" value="Back to Dashboard Page" id="back-to-dashboard-button">
    </form:form>
</body>
</html>