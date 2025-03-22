<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Completed Goals</title>
    <link rel="stylesheet" href="/css/dashboard.css" />
    <link rel="stylesheet" href="/css/completed-goals.css">
    <link rel="stylesheet" href="/css/logo.css">
</head>
<body>

<header>
    <h2>Your Completed Goals</h2>

    <div class="logo-container">
        <div class="logo">
            <span class="ibm">IBM</span>
            <span class="skillsbuild">SkillsBuild Gamification</span>
        </div>
    </div>
</header>

<main>
    <c:choose>
        <c:when test="${not empty completedGoals}">
            <ul class="goal-list">
                <c:forEach var="goal" items="${completedGoals}">
                    <li class="goal-item">
                        <strong>Goal:</strong> ${goal.numberOfGoals} Courses |
                        <strong>Completed On:</strong> <fmt:formatDate value="${goal.actualCompletionDate}" pattern="yyyy-MM-dd"/>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p class="no-goals">You have not completed any goals yet.</p>
        </c:otherwise>
    </c:choose>
</main>

<a href="${pageContext.request.contextPath}/${username}/dashboard-page" class="back-btn">Back to Dashboard</a>

</body>
</html>
