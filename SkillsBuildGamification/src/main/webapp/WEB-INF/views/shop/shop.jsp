<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link rel="stylesheet" href="/css/shop.css">
    <link rel="stylesheet" href="/css/logo.css">

</head>
<body>

<header>
    <div class="logo-container">
        <div class="logo">
            <span class="ibm">IBM</span>
            <span class="skillsbuild">SkillsBuild Gamification</span>
        </div>
    </div>
</header>
<!-- Horizontal Category Buttons -->

<div class="shop-container">
    <!-- Add CSRF token to be used in JavaScript -->
    <input type="hidden" id="csrfToken" name="${_csrf.parameterName}" value="${_csrf.token}">

    <!-- Left Side: Avatar Preview -->
    <div id="left-container">
        <h1>Your Avatar</h1>
        <img id="avatar-preview" src="${student.avatarUrl}" alt="Your Avatar">
    </div>

    <!-- Right Side: Shop Items -->
    <div id="right-container">
        <h2>Shop</h2>
        <div id="credits-container">
            <h2>Your Credits: <span id="user-credits">${student.credits}</span></h2>
        </div>
        <div id="avatar-options"></div>
    </div>
</div>

<div id="category-buttons"></div>


<script>
    var username = "${student.username}";
    console.log(username)
</script>
<script src="/javascript/avatar-shop.js"></script>

</body>
</html>
