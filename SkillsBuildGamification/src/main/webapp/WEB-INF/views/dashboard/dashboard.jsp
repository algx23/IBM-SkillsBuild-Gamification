<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Dashboard</title>
  <link rel="stylesheet" href="/css/dashboard.css" />
</head>
<body>
<main>
  <h1 class="dashboard-title">Dashboard</h1>

  <div name="navigation-filter/search">
    <!--This is to clearly separate the filter and search functions from each other and allow the users to type up the name of the course or select one from the dropdown menu-->
    <div class=filter">
      <form action id="Filter-Courses">
        <label for="courses-available">Please select the course that you want to appear on the website:</label>
        <select id="courses-available">
          <option value=""> -- select an option -- </option>
          <c:forEach var="course" items="${courses}">
            <option value="${course.courseName}">${course.courseName}</option>
          </c:forEach>
        </select>
        <button type="button" onclick="filterCourses()">Enter course filter</button>
      </form>
    </div>

    <div class="search">
      <label for="textInput">Please enter the name of the course manually that you wish to select:</label>
      <input type="text" id="textInput" placeholder="Search for course...">
      <button type="button" onclick="searchCourses()">Enter course search</button>
    </div>
  </div>

  <!--This is to get each of the courses to appear on the webpage-->
  <div class="course-container">
    <c:forEach var="course" items="${courses}">
      <div class="course-box" id="${course.courseName}">
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
  document.getElementById("courses-available").value="";
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

  function filterCourses() {
    let selectedValue, selectElement, courseBoxs, i;
    selectElement = document.getElementById("courses-available")
    selectedValue = selectElement.value;
    courseBoxs = document.getElementsByClassName("course-box")
    if (selectedValue !== "") {
      for (i = 0; i < courseBoxs.length; i++) {
        if (courseBoxs[i].id !== selectedValue) {
          courseBoxs[i].style.display = 'none';
        }
        else{
          courseBoxs[i].style.display = 'block'
        }
      }
    } else {
      for (i = 0; i < courseBoxs.length; i++) {
        if (courseBoxs[i].id !== selectedValue) {
          courseBoxs[i].style.display = 'block';
        }
      }
    }
  }

</script>

</body>
</html>

