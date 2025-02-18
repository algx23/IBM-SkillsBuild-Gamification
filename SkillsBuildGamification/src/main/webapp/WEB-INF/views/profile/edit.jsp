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

    <!-- Left Section - Edit Profile -->
    <div class="profile-left">
        <h2>Edit Profile</h2>
        <form id="editProfileForm" action="/profile/${student.username}/update" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <label for="password">New Password</label>
            <input type="password" id="password" name="password" class="input-field" placeholder="Enter new password"><br>

            <label for="bio">Bio:</label>
            <textarea id="bio" name="bio" class="input-field" placeholder="Write something about yourself...">${student.bio}</textarea><br>

            <label for="profilePic">Profile Picture URL:</label>
            <input type="text" id="profilePic" name="profilePic" class="input-field" value="${student.profilePic}" placeholder="Paste image URL here"><br>
        </form>
    </div>

    <!-- Right Section - Courses with Status Update -->
    <div class="profile-right">
        <h3>Completed Courses</h3>
        <div class="completed-courses">
            <c:forEach var="course" items="${completedCourses}">
                <div class="course-box">
                    <a href="/${student.username}/courseStatus/${course.courseID}?sourcePage=editProfile">
                            ${course.courseName}
                    </a>
                </div>
            </c:forEach>
        </div>

        <h3>In Progress Courses</h3>
        <div class="in-progress-courses">
            <c:forEach var="course" items="${inProgressCourses}">
                <div class="course-box">
                    <a href="/${student.username}/courseStatus/${course.courseID}?sourcePage=editProfile">
                            ${course.courseName}
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Common Buttons BELOW Both Sections -->
<div class="profile-actions">
    <button type="button" class="save-btn" onclick="document.getElementById('editProfileForm').submit();">Save Changes</button>
    <a href="/profile/${student.username}">
        <button class="cancel-btn">Cancel</button>
    </a>
</div>

</body>
</html>
