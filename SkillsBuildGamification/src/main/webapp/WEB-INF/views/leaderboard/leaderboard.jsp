<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Leaderboard</title>
    <style>
      /* <%--        TODO: make it look not terrible--%> */
    </style>
  </head>
  <body>
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
            <td><a href="/profile/${student.username}">${student.username}</a></td>
            <td>${student.points}</td>
        <tr>
        </c:forEach>
        
    </table>
    <br>
    <br>
    <form action="/login-success" method="get">
        <input type="submit" value="Back to Dashboard">
    </form>
  </body>
</html>
