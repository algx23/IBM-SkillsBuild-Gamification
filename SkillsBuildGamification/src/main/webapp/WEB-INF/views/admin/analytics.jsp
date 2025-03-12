<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Analytics</title>
    <!-- Stylesheets for page styling -->
    <link rel="stylesheet" href="/css/analytics.css" />
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

<main>
    <h1 class="analytics-title">Analytics</h1>

    <!-- Display list of courses -->
    <div class="course-container">
        <c:forEach var="course" items="${courses}">
            <div class="course-box" id="${course.courseName}">
                <a href="${course.courseLink}" target="_blank" data-courseid="${course.courseID}">
                        ${course.courseName}
                </a>
                <p>Students in progress: ${course.getNumStudentsInProgress()}</p>
                <p>Students who have completed this: ${course.getNumStudentsCompleted()}</p>
            </div>
        </c:forEach>
    </div>
</main>

</body>
</html>





