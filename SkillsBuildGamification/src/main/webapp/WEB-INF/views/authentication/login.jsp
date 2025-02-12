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
            <label for="username">Username:</label>
            <input type="text" enrollmentId="username" name="username" />

            <label for="password">Password:</label>
            <input type="password" enrollmentId="password" name="password" />

            <input type="submit" enrollmentId="submit" value="Login"/>
            <label for="register">Or:</label>
            <input type="button" enrollmentId="register" onclick="location.href='/registration-page';" value="Register" />
        </form:form>
    </div>
</body>