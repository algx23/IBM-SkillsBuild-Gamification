<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
<h1>Edit Your Profile</h1>
<form action="/profile/update" method="post">
    <input type="hidden" name="username" value="${student.username}">

    <label for="rank">Rank:</label>
    <input type="text" id="rank" name="rank" value="${student.rank}"><br>

    <label for="completedCourses">Completed Courses:</label>
    <input type="text" id="completedCourses" name="completedCourses" value="${student.completedCourses}"><br>

    <input type="submit" value="Update">
</form>
</body>
</html>
