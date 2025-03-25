<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: c0m6a
  Date: 20/03/2025
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="/css/see-reviews.css"/>
<head>
    <title>${course.courseName}</title>
</head>
<body>
<h1>User Reviews:</h1>
<c:forEach var="review" items="${reviews}">
    <div>
        <c:if test="${student.isAdmin()}">
            <a href="/deleteReviews?reviewID=${review.reviewId}&courseName=${course.courseName}&username=${student.username}">Delete Review</a>

        </c:if>
        Review ID:${review.reviewId}<br>
        Username:${student.username}<br>
        Course: ${course.courseName}<br>
        Rating: ${review.rating}<br>
        Users review:
        <div>
                ${review.reviewText}
        </div>
    </div>
</c:forEach>
<a href="/${student.username}/dashboard-page">Dashboard</a>
</body>
</html>
