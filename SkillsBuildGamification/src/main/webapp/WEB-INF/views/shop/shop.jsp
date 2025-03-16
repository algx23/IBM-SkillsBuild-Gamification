<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link rel="stylesheet" href="/css/shop.css">
</head>
<body>

<!-- Horizontal Category Buttons -->
<div id="category-buttons"></div>

<div class="shop-container">
    <!-- Left Side: Avatar Preview -->
    <div id="left-container">
        <h1>Your Avatar</h1>
        <img id="avatar-preview" src="${student.avatarUrl}" alt="Your Avatar">
    </div>

    <!-- Right Side: Shop Items -->
    <div id="right-container">
        <h2>Shop</h2>
        <div id="avatar-options"></div>
    </div>
</div>

<script>
    var username = "${student.username}";
    console.log(username)
</script>
<script src="/javascript/avatar-shop.js"></script>

</body>
</html>
