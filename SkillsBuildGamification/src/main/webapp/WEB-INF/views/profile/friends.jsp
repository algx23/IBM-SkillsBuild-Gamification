<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/friends.css"></link>
    <title>Your Friends</title>
</head>
<body>
<h1>Your Friends: </h1>
<p>Here you can see your friends and view their profile also!</p>
    <form:form action="/${username}/dashboard-page" method="get">
        <input type="submit" value="Back to Dashboard Page">
    </form:form>
    <c:forEach var="f" items="${friends}">
        <div class="friend-container">
            <div class="profile-pic-container">
                <img src="${f.profilePic}" alt="NoImageFound" class="profile-pic">
            </div>
            <h2>${f.username}</h2>
            <input class="view-profile-button" type="button" onclick=window.location.href="/profile/${f.username}" value="View Profile">
        </div>
    </c:forEach>
</body>
</html>