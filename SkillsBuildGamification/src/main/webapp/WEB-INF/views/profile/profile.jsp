<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="/css/profile.css">
</head>
<body>
<div class="profile-container">
    <div class="profile-left">
        <div class="profile-pic-container">
            <img src="${student.profilePic}" class="profile-pic" alt=" ">
        </div>
        <h2>${student.username}</h2>
        <p><strong>Level:</strong> ${student.level}</p>
        <p><strong>Points:</strong> ${student.points}</p>
        <p><strong>Leaderboard Position:</strong> ${leaderboardPosition}</p>
        <p><strong>Bio:</strong> ${student.bio}</p>

        <a href="/profile/${student.username}/edit">
            <button class="edit-profile-btn">Edit Profile</button>
        </a>

        <form action="/login-success" method="get">
            <button class="back-to-dashboard">Back to Dashboard</button>
        </form>
    </div>

    <div class="profile-right">
        <h3>Completed Courses</h3>
        <div class="completed-courses">
            <c:forEach var="courseName" items="${student.completedCourses.split(', ')}">
                <div class="course-box">
                    <p>${courseName}</p> <!-- course name only for now -->
                </div>
            </c:forEach>

        </div>
    </div>
</div>
</body>
</html>
