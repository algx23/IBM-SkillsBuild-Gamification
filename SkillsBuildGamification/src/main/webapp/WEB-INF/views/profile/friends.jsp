<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your Friends</title>
</head>
<body>
<h1>Your Friends: </h1>
<p>Here you can see your friends and view their profile also!</p>
    <c:forEach var="f" items="${friends}">
        <div>
            <h2>${f.username}</h2>
            <input type="button" onclick=window.location.href="/profile/${f.username}" value="View Profile">
        </div>
    </c:forEach>
</body>
</html>