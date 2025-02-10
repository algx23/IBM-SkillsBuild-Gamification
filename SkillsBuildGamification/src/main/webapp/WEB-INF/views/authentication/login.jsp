<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <div class="login-container">
        <form:form class="form" action="/login-process" method="post" modelAttribute="user">
            <c:if test="${failure == true}">
                <p class="error">Could not log in. Please ensure that the username and password are entered correctly.</p>
            </c:if>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" />

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" />

            <input type="submit" id="submit" value="Login"/>
            <label for="register">Or:</label>
            <input type="button" id="register" onclick="location.href='/registration-page';" value="Register" />
        </form:form>
    </div>
</body>