<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Denied</title>
</head>
<body>
<main>
    <h1>Dashboard</h1>
    <p>
        <c:forEach var="course" items="${courses}">
            <li>${course.courseName}</li>
        </c:forEach>
    </p>
</main>
</body>