<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Register</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
    <div class="login-container">
        <form:form class="form" action="/registration-process" method="post" modelAttribute="student">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" />

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" />

            <input type="submit" id="submit" value="Register"/>
        </form:form>
    </div>
</body>