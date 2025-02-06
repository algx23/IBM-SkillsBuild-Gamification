<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Welcome to Your Dashboard</h1>

<p><a href="/leaderboard">View Leaderboard</a></p>
<p><a href="/profile/${username}">View My Profile</a></p>

<form action="/logout" method="post">
    <input type="submit" value="Logout">
</form>
</body>
</html>
