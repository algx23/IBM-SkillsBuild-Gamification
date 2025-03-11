<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <input type="submit" value="Accept Request">
            <input type="submit" value="Reject Request">
        </div>
    </c:forEach>
</body>
</html>