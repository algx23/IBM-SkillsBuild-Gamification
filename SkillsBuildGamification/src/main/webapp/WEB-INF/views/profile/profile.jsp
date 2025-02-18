<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="/css/profile.css">
    <link rel="stylesheet" href="/css/logo.css">
</head>
<body>
<header>
    <div class="logo-container">
        <div class="logo">
            <span class="ibm">IBM</span>
            <span class="skillsbuild">SkillsBuild Gamification</span>
        </div>
    </div>
</header>
<div class="profile-container">

    <!-- Left Section - Profile Details -->
    <div class="profile-left">
        <div class="profile-pic-container">
            <img src="${student.profilePic}" class="profile-pic" alt=" ">
        </div>
        <h2>${student.username}</h2>
        <p><strong>Level:</strong> ${student.level}</p>
        <p><strong>Rank: </strong>${student.userRank}</p>
        <img id="rankImg" src="<c:url value="/${url}/"/>" alt="image didnt load"/>        <p><strong>Points:</strong> ${student.points}</p>
        <p><strong>Leaderboard Position:</strong> ${leaderboardPosition}</p>
        <!-- Badges Section -->
        <p><strong>Badges:</strong></p>
        <div class="badge-container">
            <c:forEach var="badge" items="${student.earnedBadges}">
                <div class="badge-box">
                    <img src="/images/badges/${badge}.png" class="badge-icon">
                    <div class="badge-tooltip">
                        <strong>${badge}</strong><br>
                        Earned by completing ${badgeRequirements[badge]} course(s)
                    </div>
                </div>
            </c:forEach>
        </div>
        <p><strong>Bio:</strong> ${student.bio}</p>
    </div>

    <!-- Right Section - Courses -->
    <div class="profile-right">
        <h3>Completed Courses</h3>
        <div class="completed-courses">
            <c:forEach var="course" items="${completedCourses}">
                <div class="course-box">
                    <a href="${course.courseLink}" target="_blank">${course.courseName}</a>
                </div>
            </c:forEach>
        </div>

        <h3>In Progress Courses</h3>
        <div class="in-progress-courses">
            <c:forEach var="course" items="${inProgressCourses}">
                <div class="course-box in-progress">
                    <a href="${course.courseLink}" target="_blank">${course.courseName}</a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Common Buttons BELOW Both Sections -->
<div class="profile-actions">
    <c:if test="${isOwner}">
        <a href="/profile/${student.username}/edit">
            <button class="save-btn">Modify Details & Courses</button>
        </a>
    </c:if>
    <form action="/login-success" method="get">
        <button class="back-btn">Back to Dashboard</button>
    </form>
</div>

<!-- Logout Button -->
<div class="logout-container">
    <form action="/logout" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="logout-btn">Logout</button>
    </form>
</div>

</body>
</html>
