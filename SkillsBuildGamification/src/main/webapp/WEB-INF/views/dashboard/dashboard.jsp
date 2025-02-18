<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Dashboard</title>
  <link rel="stylesheet" href="/css/dashboard.css" />
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
  <h1 class="dashboard-title">Dashboard</h1>
  <div class="course-container">
    <c:forEach var="course" items="${courses}">
      <div class="course-box">
        <a href="${course.courseLink}" target="_blank" data-courseid="${course.courseID}">
            ${course.courseName}
        </a>
      </div>
    </c:forEach>
  </div>

  <div class="redirect-container">
    <div class="redirect-box">
      <a href="<c:url value='/leaderboard' />">Leaderboard</a>
    </div>
    <div class="redirect-box">
      <a href="<c:url value='/profile/${username}' />">User Profile</a>
    </div>
  </div>

</main>


<script>
  document.addEventListener("DOMContentLoaded", function () {
    let lastCourseID = localStorage.getItem("lastCourseID");
    if (lastCourseID) {
      localStorage.removeItem("lastCourseID");
      window.location.href = "/${username}/courseStatus/" + lastCourseID;
    }

    document.querySelectorAll(".course-box a").forEach(link => {
      link.addEventListener("click", function () {
        let courseID = this.getAttribute("data-courseid");
        localStorage.setItem("lastCourseID", courseID);
      });
    });

    document.addEventListener("visibilitychange", function () {
      if (document.visibilityState === "visible") {
        location.reload();
      }
    });
  });
</script>

</body>
</html>

