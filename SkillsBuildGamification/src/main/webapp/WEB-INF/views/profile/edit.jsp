<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="/css/edit-profile.css">
</head>
<body>
<div class="profile-container">
    <div class="profile-left">
        <h2>Edit Profile</h2>
        <form action="/profile/${student.username}/update" method="post">

            <!-- csrf token to allow changes -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <label>New Username:</label>
            <input type="text" name="newUsername" class="input-field" value="${student.username}" required><br>

            <label>New Password</label>
            <input type="password" name="password" class="input-field"><br>

            <label>Bio:</label>
            <textarea name="bio" class="input-field">${student.bio}</textarea><br>

            <label>Profile Picture URL:</label>
            <input type="text" name="profilePic" class="input-field" value="${student.profilePic}"><br>

            <button type="submit" class="save-btn">Save Changes</button>
        </form>

        <a href="/profile/${student.username}">
            <button class="cancel-btn">Cancel</button>
        </a>
    </div>

    <!-- Completed Courses -->
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
