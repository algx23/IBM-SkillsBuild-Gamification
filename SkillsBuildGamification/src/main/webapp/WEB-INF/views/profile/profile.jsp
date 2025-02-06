<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<h1>Welcome, ${student.username}!</h1>
<p><strong>Rank:</strong> ${student.level}</p>
<p><strong>Leaderboard Position:</strong> ${student.leaderboardPosition}</p>
<p><strong>Completed Courses:</strong> ${student.completedCourses}</p>

<a href="/profile/${student.username}/edit">Edit Profile</a>
</body>
</html>
