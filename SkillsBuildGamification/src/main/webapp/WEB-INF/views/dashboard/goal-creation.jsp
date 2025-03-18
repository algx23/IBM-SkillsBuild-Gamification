<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create Goal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create-goal.css">
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

<div class="create-goal-container">
    <h2>Create a Goal</h2>

    <form:form modelAttribute="goal" action="${pageContext.request.contextPath}/${username}/save-goal" method="post">
        <label for="numberOfGoals">Number of Courses:</label>
        <form:input type="number" path="numberOfGoals" id="numberOfGoals" required="true"/>
        <br>

        <label for="completionDate">To be completed by:</label>
        <form:input type="date" path="completionDate" id="completionDate" required="true"/>
        <br>

        <button type="submit" class="create-goal-button">Create Goal</button>
    </form:form>

    <!-- Navigation Buttons -->
    <div class="redirect-container">
        <div class="redirect-box">
            <a href="${pageContext.request.contextPath}/${username}/dashboard-page">Back to Dashboard</a>
        </div>
    </div>
</div>

</body>
</html>


