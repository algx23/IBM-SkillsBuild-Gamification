<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Dashboard</title>
  <!-- Stylesheets for page styling -->
  <link rel="stylesheet" href="/css/dashboard.css" />
  <link rel="stylesheet" href="/css/logo.css">
  <link rel="stylesheet" href="/css/search-filter.css">
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

  <div class="navigation-filter-search">
    <!-- Course filtering section -->
    <div class="filter">
      <form action id="Filter-Courses">
        <label for="courses-available">Select course progression:</label>
        <select id="courses-available">
          <option value=""> -- select an option -- </option>
          <option value="COMPLETED">Completed</option>
          <option value="IN_PROGRESS">In progress</option>
          <option value="NOT_STARTED">Not started</option>
        </select>
        <button type="button" onclick="filterCourses()">Apply</button>
      </form>
    </div>

    <!-- Course search section -->
    <div class="search">
      <label for="textInput">Search for a course:</label>
      <input type="text" id="textInput" placeholder="Type course name...">
      <button type="button" onclick="searchCourses()">Search</button>
    </div>
  </div>

  <!-- Display list of courses and reviews -->
  <div class="course-container">
    <c:forEach var="course" items="${courses}">
      <div class ="review-course-section" id="${course.courseName}">
        <div class="course-box" id="${course.courseName}">
          <a href="${course.courseLink}" target="_blank" data-courseid="${course.courseID}">
              ${course.courseName}
          </a>
        </div>
        <c:forEach var="enrollment" items="${enrollments}">
          <c:if test="${enrollment.status == 'COMPLETED' && enrollment.course.courseName == course.courseName}">
            <a href = "<c:url value='/addReviews?courseName=${course.courseName}&username=${username}' />">
              Add Reviews
            </a>
          </c:if>
        </c:forEach>
        <a href = "<c:url value='/seeReviews?courseName=${course.courseName}&username=${username}' />">
          See reviews
        </a>
      </div>
    </c:forEach>
  </div>

  <!-- Navigation buttons to other pages -->
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
  // Reset the dropdown selection when the page loads
  document.getElementById("courses-available").value="";

  document.addEventListener("DOMContentLoaded", function () {
    let lastCourseID = localStorage.getItem("lastCourseID");

    // Redirect to last visited course status page if stored
    if (lastCourseID) {
      localStorage.removeItem("lastCourseID");
      window.location.href = "/${username}/courseStatus/" + lastCourseID;
    }

    // Store course ID in localStorage when a course is clicked
    document.querySelectorAll(".course-box a").forEach(link => {
      link.addEventListener("click", function () {
        let courseID = this.getAttribute("data-courseid");
        localStorage.setItem("lastCourseID", courseID);
      });
    });

    // Reload the page when the tab is reactivated
    document.addEventListener("visibilitychange", function () {
      if (document.visibilityState === "visible") {
        location.reload();
      }
    });
  });

  // Filter courses based on selected status
  function filterCourses() {
    let selectedValue = document.getElementById("courses-available").value;
    let courseBoxes = document.getElementsByClassName("review-course-section");

    // Hide all courses initially
    for (let i = 0; i < courseBoxes.length; i++) {
      courseBoxes[i].style.display = 'none';
    }

    <c:forEach var="enrollment" items="${enrollments}">
    switch (selectedValue) {
      case "COMPLETED":
        for (let i = 0; i < courseBoxes.length; i++) {
          if (courseBoxes[i].id === "${enrollment.course.courseName}" && "${enrollment.status}" === "COMPLETED") {
            courseBoxes[i].style.display = 'block';
          }
        }
        break;
      case "IN_PROGRESS":
        for (let i = 0; i < courseBoxes.length; i++) {
          if (courseBoxes[i].id === "${enrollment.course.courseName}" && "${enrollment.status}" === "IN_PROGRESS") {
            courseBoxes[i].style.display = 'block';
          }
        }
        break;
    }
    </c:forEach>

    // Show courses that have not been started
    if (selectedValue === "NOT_STARTED") {
      let enrolledCourses = new Set();

      <c:forEach var="enrollment" items="${enrollments}">
      enrolledCourses.add("${enrollment.course.courseName}");
      </c:forEach>

      for (let i = 0; i < courseBoxes.length; i++) {
        let courseName = courseBoxes[i].id;
        if (!enrolledCourses.has(courseName)) {
          courseBoxes[i].style.display = 'block';
        }
      }
    }

    // If no filter is selected, show all courses
    if (selectedValue === "") {
      for (let i = 0; i < courseBoxes.length; i++) {
        courseBoxes[i].style.display = 'block';
      }
    }
  }

  // Search function for filtering courses by name
  function searchCourses() {
    var input, filter, courseBoxes, i, courseName;
    input = document.getElementById("textInput");
    filter = input.value.toUpperCase().split(' ');

    courseBoxes = document.getElementsByClassName("review-course-section");

    // Hide all courses initially
    for (let i = 0; i < courseBoxes.length; i++) {
      courseBoxes[i].style.display = 'none';
    }

    // Show only courses matching the search input
    for (let i = 0; i < courseBoxes.length; i++) {
      courseName = courseBoxes[i].id.toUpperCase();
      let match = true;
      for (let word of filter) {
        if (!courseName.includes(word)) {
          match = false;
          break;
        }
      }
      if (match) {
        courseBoxes[i].style.display = "block";
      }
    }
  }
</script>


</body>
</html>

