<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Course Status</title>
    <link rel="stylesheet" href="/css/dashboard.css" />
</head>
<body>
<main>
    <h1 class="status-title">Update Course Status</h1>
    <h2>${course.courseName}</h2>

    <form action="<c:url value='/${username}/courseStatus/${course.courseID}' />" method="post">
        <div class="status-container">
            <label class="status-box">
                <input type="radio" name="status" value="completed"/>
                Course Completed
            </label>
            <label class="status-box">
                <input type="radio" name="status" value="inProgress" />
                Course In Progress
            </label>
        </div>
        <button type="submit" class="submit-button">Save Status</button>
    </form>
</main>
</body>
</html>
