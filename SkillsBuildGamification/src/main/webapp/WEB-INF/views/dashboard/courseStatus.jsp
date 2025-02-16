<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Course Status</title>
    <link rel="stylesheet" href="/css/dashboard.css" />

    <script> <!-- Needs proper implementation upon correct button name by John-->
        function handleNotStartedSelection() {
            alert("Redirecting to the dashboard...");
            window.location.href = "/${username}/dashboard-page";
        }
    </script>
</head>
<body>
<main>
    <h1 class="status-title">Update Course Status</h1>
    <h2>${course.courseName}</h2>

    <form action="<c:url value='/${username}/courseStatus/${course.courseID}' />" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <!-- Track where the request is coming from -->
        <input type="hidden" name="sourcePage" value="${param.sourcePage}" />

        <div class="status-container">
            <label class="status-box">
                <input type="radio" name="status" value="COMPLETED"
                ${courseStatus eq 'COMPLETED' ? 'checked' : ''} required />
                Course Completed
            </label>
            <label class="status-box">
                <input type="radio" name="status" value="IN_PROGRESS"
                ${courseStatus eq 'IN_PROGRESS' ? 'checked' : ''} required />
                Course In Progress
            </label>
        </div>

        <button type="button" class="back-button" onclick="handleNotStartedSelection()">Go Back</button>

        <button type="submit" class="submit-button">Save Status</button>
    </form>
</main>
</body>
</html>
