<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css" />
  </head>
  <body>
    <main>
      <h1 class="dashboard-title">Dashboard</h1>
      <div class="container">
        <c:forEach var="course" items="${courses}">
          <div class="course-box">
            <a href="${course.courseLink}" target="_blank"
              >${course.courseName}</a
            >
          </div>
        </c:forEach>
      </div>
    </main>
  </body>
</html>
