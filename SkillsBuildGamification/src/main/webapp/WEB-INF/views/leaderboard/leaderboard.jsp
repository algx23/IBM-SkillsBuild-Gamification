<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
      <link rel="stylesheet" href="css/leaderboard.css">
      <script src="javascript/leaderboard.js" defer></script>
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
        <form:form method="post" action="leaderboard?sortBy=HighestCourseFirst">
          <input type="submit" value="Courses Completed (high to low)" class="sort-button">
        </form:form>
      </li>

            <li>
        <form:form method="post" action="leaderboard?sortBy=LowestCourseFirst">
          <input type="submit" value="Courses Completed (low to high)" class="sort-button">
        </form:form>
      </li>

      
      <li>
          <form:form method="post" action="leaderboard?sortBy=">
            <input type="submit" value="Clear All Sorting" class="clearButton">
          </form:form>
      </li>

  </ul>

  <p>Current Sort: ${sortingOrder}</p>

  <div class="table-container">
    <table>
        <thead>
        <tr>
            <th>Position</th>
            <th>Username</th>
            <th>Points</th>
            <th>Completed Courses</th>
            <th>View Profile</th>
        </tr>
        </thead>
        
        <tbody>
          <!-- always display the logged in student first and then the rest, as if there is many students, it
           may be hard to find themselves in the middle of thousands of studnets -->
                  <tr class="currentlyLoggedIn">
                    <td class="currentlyLoggedIn">${loggedInStudentPos}</td>
                    <td class="currentlyLoggedIn">${loggedInStudent.username}</td>
                    <td class="currentlyLoggedIn">${loggedInStudent.points}</td>
                    <td class="currentlyLoggedIn">${loggedInStudent.numCompletedCourses}</td>
                    <td class="buttonRow"><input type="button" onclick="window.location.href='/profile/${loggedInStudent.username}'" value="View and edit your profile!" class="button"></td>
                </tr>
      <c:forEach var="student" items="${students}" varStatus="s">
        <!-- for each row, the username has a url which goes to that users' profile
         the href link for it will be /profile/{username of the user in the table}.
         This is handled in the /{username} endpoint in the ProfileController, written by Shoaib. -->

        <c:choose>
            <c:when test="${student.username eq loggedInStudent.username}">
                <tr class="currentlyLoggedIn">
                  <!-- if the currently logged in student happens to be in the top 3
                   in the leaderboard, I want to change the color of the position
                   indicator. For this reason, i added the class "Position" to the 
                   appropriate <td> tag also-->
                    <td class="currentlyLoggedIn Position">${s.index + 1}</td>
                    <td class="currentlyLoggedIn">${student.username}</td>
                    <td class="currentlyLoggedIn ">${student.points}</td>
                    <td class="currentlyLoggedIn ">${coursesCompleted[s.index]}</td>
                    <td class="buttonRow"><input type="button" onclick="window.location.href='/profile/${student.username}'" value="View and edit your profile!" class="button"></td>

                </tr>
            </c:when>
        <c:otherwise>
        <tr>
            <td class="Position">${s.index + 1}</td>
            <td >${student.username}</td>
            <td >${student.points}</td>
            <td >${coursesCompleted[s.index]}</td>
            <td class="buttonRow">
            <input type="button" onclick="window.location.href='/profile/${student.username}'" value="View ${student.username}'s profile!" class="button">
            </td>
          </tr>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    </tbody>
    </table>
      </div>
    <br>
    <form action="/login-success" method="get">
        <input type="submit" value="Back to Dashboard" class="back-to-dboard-button">
    </form>
  </body>
</html>
