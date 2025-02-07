<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
<h1>Edit Your Profile</h1>
<form action="/profile/update" method="post">
    <input type="hidden" name="username" value="${student.username}">

    <label for="level">Rank:</label>
    <input type="text" id="level" name="level" value="${student.level}"><br>

    <label for="completedCourses">Completed Courses:</label>
    <input type="text" id="completedCourses" name="completedCourses" value="${student.completedCourses}"><br>

    <input type="submit" value="Update">
</form>
</body>
</html>
