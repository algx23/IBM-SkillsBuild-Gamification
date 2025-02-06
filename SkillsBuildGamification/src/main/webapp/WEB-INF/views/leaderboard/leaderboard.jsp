<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
      <link rel="stylesheet" href="css/leaderboard.css">
    <title>Leaderboard</title>
    <style>
      /* <%--        TODO: make it look not terrible--%> */
    </style>
  </head>
  <body>

  <h1>Leaderboards</h1>
  <h2>Global Leaderboard</h2>
  <p>Here is the global leaderboard. See how you compare to others, and complete more courses to climb up the ranks, and win a prize!</p>
    <table>
        <tr>
            <th>Username</th>
            <th>Points</th>
        </tr>

      <c:forEach var="student" items="${students}">
        <!-- for each row, the username has a url which goes to that users' profile
         the href link for it will be /profile/{username of the user in the table}.
         This is handled in the /{username} endpoint in the ProfileController, written by Shoaib. -->
        <tr>
            <td>${student.username}</td>
            <td>${student.points}</td>
            <td>
            <input type="button" onclick="window.location.href='/profile/${student.username}'" value="View ${student.username}'s profile!" class="button">
            </td>
        <tr>
        </c:forEach>

    </table>

  </body>
</html>
