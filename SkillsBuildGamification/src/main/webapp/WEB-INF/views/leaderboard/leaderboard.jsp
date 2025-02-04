<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Leaderboard</title>
    <style>
<%--        TODO: make it look not terrible--%>
    </style>
</head>
<body>
    <c:forEach var="student" items="${students}">
        <p>username: ${student.username}, points: ${student.points}</p>
    </c:forEach>

</body>
</html>