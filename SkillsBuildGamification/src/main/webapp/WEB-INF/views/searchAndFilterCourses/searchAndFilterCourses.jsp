<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Courses:</h2>
<div>
    <table>
        <tr>
            <th>Course Name</th>
            <th>Course ID</th>
            <th>Course Completion Status</th>
            <th>Course Link</th>
        </tr>
        <c:forEach items ="${enroledCourses}" var = "studentEnrolment">
            <td>.getCourseName()</td>
            <td>courseData.getCourseID()</td>
            <td></td>
            <td>courseData.getCourseLink()</td>
        </c:forEach>
        <c:forEach items ="${enroledCourses}" var = "studentEnrolment">

            <c:if test="${}"
            <td>.getCourseName()</td>
            <td>courseData.getCourseID()</td>
            <td></td>
            <td>courseData.getCourseLink()</td>
        </c:forEach>
    </table>
</div>
<p>

</p>
</body>
</html>