<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
            <a href="<c:url value='/shop' />"><img src="${student.avatarUrl}" class="profile-pic" alt=" avatar not loading ">
            </a>
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
                    <img src="/images/badges/${badge}.png" class="badge-icon"> <!-- badge png -->
                    <div class="badge-tooltip">
                        <strong>${badge}</strong><br> <!-- Info displayed over hover -->
                        Earned by completing ${badgeRequirements[badge]} course(s)
                    </div>
                </div>
            </c:forEach>
        </div>
        <p><strong>Shop Credits: </strong>${student.credits}</p>
        <p><strong>Bio:</strong> ${student.bio}</p>
        <br>
        <br>
        <c:if test="${!isOwner}">
            <form:form action="/sendRequest/${student.username}" method="post">
                <button class="send-fr-button">Send Friend Request</button>
            </form:form>
        </c:if>
    </div>

    <!-- Right Section - Courses -->
    <div class="profile-right">
        <h3>Completed Courses</h3> <!-- Separate in progress course -->
        <div class="completed-courses">
            <c:forEach var="course" items="${completedCourses}">
                <div class="course-box">
                    <a href="${course.courseLink}" target="_blank">${course.courseName}</a>
                </div>
            </c:forEach>
        </div>

        <h3>In Progress Courses</h3> <!-- Separate in progress course -->
        <div class="in-progress-courses">
            <c:forEach var="course" items="${inProgressCourses}">
                <div class="course-box in-progress">
                    <a href="${course.courseLink}" target="_blank">${course.courseName}</a>
                </div>
            </c:forEach>
        </div>
    </div>


    <c:if test="${isOwner}">
        <div class="profile-bottom-right">
            <h2>Manage Friends: </h2>
                <button onclick=window.location.href="/profile/${student.username}/friend-requests" class="friend-actions">See Friend Requests</button>
                <br>
                <button onclick=window.location.href="/profile/${student.username}/friends" class="friend-actions">See Friends</button>
        </div>
    </c:if>
</div>

<!-- Common Buttons BELOW Both Sections -->
<div class="profile-actions">
    <c:if test="${isOwner}">
        <a href="/shop">
            <button class="save-btn">Edit Avatar</button>
        </a>
        <a href="/profile/${student.username}/edit">
            <button class="save-btn">Modify Details & Courses</button>
        </a>
    </c:if>
    <form action="/${student.username}/dashboard-page" method="get">
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
