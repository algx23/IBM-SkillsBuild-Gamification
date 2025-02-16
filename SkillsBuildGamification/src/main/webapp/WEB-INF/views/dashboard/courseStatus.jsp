<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Course Status</title>
    <link rel="stylesheet" href="/css/dashboard.css" />

    <script>
        function handleNotStartedSelection() {
            alert("Redirecting to the dashboard...");
            window.location.href = "/${username}/dashboard-page";
        }

        window.onload = function() {
            <c:if test="${not empty successMessage}">
            alert("${successMessage}");
            window.location.href = "/${username}/dashboard-page";
            </c:if>

            <c:if test="${not empty error}">
            alert("${error}");
            </c:if>
        };
    </script>
</head>
<body>
<main>
    <h1 class="status-title">Update Course Status</h1>
    <h2>${course.courseName}</h2>

    <form action="<c:url value='/${username}/courseStatus/${course.courseID}' />" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

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



