<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Course Status</title>

    <!-- Stylesheets for page layout -->
    <link rel="stylesheet" href="/css/dashboard.css" />
    <link rel="stylesheet" href="/css/logo.css">

    <script>
        /**
         * Handles redirection when the user selects "Go Back".
         * Redirects the user based on the source page (either dashboard or edit profile).
         */
        function handleNotStartedSelection() {
            let sourcePage = "${param.sourcePage}";
            let redirectUrl = sourcePage === "editProfile"
                ? "/profile/${username}/edit"
                : "/${username}/dashboard-page";

            window.location.href = redirectUrl;
        }
    </script>
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
    <h1 class="status-title">Update Course Status</h1>
    <h2>${course.courseName}</h2> <!-- Display course name -->

    <!-- Form to update course status -->
    <form action="<c:url value='/${username}/courseStatus/${course.courseID}/update' />" method="post">
        <!-- CSRF token for security -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <!-- Track where the request is coming from -->
        <input type="hidden" name="sourcePage" value="${param.sourcePage}" />

        <div class="status-container">
            <!-- Radio button to select "Course Completed" -->
            <label class="status-box">
                <input type="radio" name="status" value="COMPLETED"
                ${courseStatus eq 'COMPLETED' ? 'checked' : ''} required />
                Course Completed
            </label>

            <!-- Radio button to select "Course In Progress" -->
            <label class="status-box">
                <input type="radio" name="status" value="IN_PROGRESS"
                ${courseStatus eq 'IN_PROGRESS' ? 'checked' : ''} required />
                Course In Progress
            </label>
        </div>

        <div class="button-container">
            <!-- Button to cancel and go back -->
            <button type="button" class="button-style" onclick="handleNotStartedSelection()">Go Back</button>
            <!-- Submit button to save the selected status -->
            <button type="submit" class="button-style">Save Status</button>
        </div>
    </form>
</main>

</body>
</html>

