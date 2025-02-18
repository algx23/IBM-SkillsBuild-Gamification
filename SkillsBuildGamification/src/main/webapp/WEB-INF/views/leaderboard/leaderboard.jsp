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
  <br>
  <div class="info-box">
  <p id="sort-method">Current Sort: ${sortingOrder}. </p>
    <p>Positions are only shown when sorting by Highest Points First (this is the default, and you can go back to this by selecting "Clear all sorting" above).</p>
    <p>Rewards are based off points.</p>
    </div>

  <h3>Your Position in the leaderboard</h3>
  <br>
  <p>Here is your position in the leaderboard, using the current sort. You can scroll down further to see the full leaderboard</p>
  <div class="table-container">
  <table>
      <thead>
      <td>Position</td>
      <td>Username</td>
      <td>Points</td>
      <td>Completed Courses</td>
      <td>View Profile</td>
      </thead>

      <tbody>
      <td>${loggedInStudentPos}</td>
      <td>${loggedInStudent.username}</td>
      <td>${loggedInStudent.points}</td>
      <td>${loggedInStudent.numCompletedCourses}</td>
      <td class="buttonRow"><input type="button" onclick="window.location.href='/profile/${loggedInStudent.username}'" value="View and edit your profile!" class="button"></td>
      </tbody>
  </table>
  </div>

  <br>
  <br>

  <h2>Global Leaderboard</h2>
  <p>Here is the global leaderboard. See how you compare to others, and complete more courses to climb up the ranks, and win a prize!</p>


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
                    <c:choose>
                        <c:when test="${sortingOrder != 'HighestScoreFirst'}">
                            <td>-</td>
                        </c:when>
                        <c:otherwise>
                            <td class="currentlyLoggedIn Position">${leaderboardPos[s.index]}</td>
                        </c:otherwise>
                    </c:choose>
                    <td class="currentlyLoggedIn">${student.username}</td>
                    <td class="currentlyLoggedIn ">${student.points}</td>
                    <td class="currentlyLoggedIn ">${coursesCompleted[s.index]}</td>
                    <td class="buttonRow"><input type="button" onclick="window.location.href='/profile/${student.username}'" value="View and edit your profile!" class="button"></td>

                </tr>
            </c:when>
        <c:otherwise>
        <tr>

<%--            if the sorting order is not via points, then the positions will just show a placeholder, as positions for
                badges and rewards are only based off who has the most points
--%>
            <c:choose>
                <c:when test="${sortingOrder != 'HighestScoreFirst'}">
                    <td>-</td>
                </c:when>
                <c:otherwise>
                <td class="Position">${leaderboardPos[s.index]}</td>
                </c:otherwise>
            </c:choose>

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
