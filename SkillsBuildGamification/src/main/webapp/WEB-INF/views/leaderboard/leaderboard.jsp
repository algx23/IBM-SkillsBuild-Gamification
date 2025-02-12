<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
      <link rel="stylesheet" href="css/leaderboard.css">
    <title>Leaderboard</title>
  </head>
  <body>

  <h1>Leaderboards</h1>
  <h2>Global Leaderboard</h2>
  <p>Here is the global leaderboard. See how you compare to others, and complete more courses to climb up the ranks, and win a prize!</p>

<%--  form to sort by points descending--%>
  <p>Sort By: </p>
  <ul>

      <li>
          <form:form method="post" action="leaderboard?sortBy=lowestScoreFirst">
            <input type="submit" value="Lowest Points first" class="sort-button">
          </form:form>
      </li>

      <li>
          <form:form method="post" action="leaderboard?sortBy=A-Z">
              <input type="submit" value="Username (A-Z)" class="sort-button">
          </form:form>
      </li>

      <li>
          <form:form method="post" action="leaderboard?sortBy=Z-A">
              <input class="sort-button"type="submit" value="Username (Z-A)">
          </form:form>
      </li>

      <li>
          <form:form method="post" action="leaderboard?sortBy=">
            <input type="submit" value="Clear All Sorting" class="clearButton">
          </form:form>
      </li>
  </ul>

    <table>
        <tr>
            <th>Username</th>
            <th>Points</th>
            <th>Completed Courses</th>
            <th>View Profile</th>
        </tr>

      <c:forEach var="student" items="${students}">
        <!-- for each row, the username has a url which goes to that users' profile
         the href link for it will be /profile/{username of the user in the table}.
         This is handled in the /{username} endpoint in the ProfileController, written by Shoaib. -->

        <c:choose>
            <c:when test="${student.username eq loggedInStudentUsername}">
                <tr enrollmentId="currentlyLoggedIn">
                    <td class="currentlyLoggedIn">${student.username}</td>
                    <td class="currentlyLoggedIn">${student.points}</td>
                    <td class="currentlyLoggedIn">${student.completedCourses}</td>
                    <td class="buttonRow"><input type="button" onclick="window.location.href='/profile/${student.username}'" value="View and edit your profile!" class="button"></td>

                </tr>
            </c:when>
        <c:otherwise>
        <tr>
            <td>${student.username}</td>
            <td>${student.points}</td>
            <td>${student.completedCourses}</td>
            <td class="buttonRow">
            <input type="button" onclick="window.location.href='/profile/${student.username}'" value="View ${student.username}'s profile!" class="button">
            </td>
        <tr>
        </c:otherwise>
        </c:choose>
        </c:forEach>

    </table>
    <br>
    <br>
    <form action="/login-success" method="get">
        <input type="submit" value="Back to Dashboard" class="back-to-dboard-button">
    </form>
  </body>
</html>
