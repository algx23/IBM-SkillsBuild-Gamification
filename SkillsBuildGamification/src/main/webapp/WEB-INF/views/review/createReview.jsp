<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: c0m6a
  Date: 20/03/2025
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="/css/add-review.css"/>
  <head>
    <title>View the reviews</title>
  </head>
  <body>
  <h1>${course.courseName}</h1>
  <%--@elvariable id="review" type="com.example.skillsbuildgamification.model.Review"--%>
  <form:form action="/createReview?courseName=${course.courseName}&username=${student.username}" modelAttribute="review" >
    <!--
    <div class="star-rating">
      <span class="star" data-value="1">&#9733;</span>
      <span class="star" data-value="2">&#9733;</span>
      <span class="star" data-value="3">&#9733;</span>
      <span class="star" data-value="4">&#9733;</span>
      <span class="star" data-value="5">&#9733;</span>
    </div>
    -->
    <label for="rating">Select a number 1-5 from the dropdown menu. (5 meaning the course was brilliant and 1 meaning it was terrible).</label>
    <form:select id ="rating" path="rating">
      <option value="5">5</option>
      <option value="4">4</option>
      <option value="3">3</option>
      <option value="2">2</option>
      <option value="1">1</option>
    </form:select>
    <label for="reviewTextd">Enter your review into you text box below</label>
    <form:input type="text" id="reviewTextd" maxlength="500" path="reviewText"/>
    <input type="submit">
  </form:form>
  </body>
</html>

<script>
  /*
  document.addEventListener('DOMContentLoaded', function () {
    const stars = document.querySelectorAll('.star');

    //this loop means that for each star element
    stars.forEach(star => {
      //it listens if the user has clicked onto any of the stars
      star.addEventListener('click', () => {
        //when a star is clicked on
        stars.forEach(s => s.classList.remove('selected'));
        star.classList.add('selected');
        let previousSibling = star.previousElementSibling;
        while (previousSibling) {
          previousSibling.classList.add('selected');
          previousSibling = previousSibling.previousElementSibling;
        }
      });
    });
  });

   */
</script>