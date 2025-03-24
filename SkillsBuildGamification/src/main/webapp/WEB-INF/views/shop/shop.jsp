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

<!-- CSRF Token -->
<input type="hidden" id="csrfToken" name="${_csrf.parameterName}" value="${_csrf.token}">

<!-- Main container -->
<div class="shop-container">

    <!-- LEFT: Avatar Preview -->
    <div id="left-container">
        <h1>Your Avatar</h1>
        <img id="avatar-preview" src="${student.avatarUrl}" alt="Your Avatar">
        <button id="back-dashboard" onclick="window.location.href='/login-success'">Back to Dashboard</button>
    </div>

    <!-- RIGHT: Tabbing Area -->
    <div id="right-container">
        <!-- Tabs -->
        <div id="tab-container">
            <button class="tab-btn active" onclick="switchTab('shop-tab')">Shop</button>
            <button class="tab-btn" onclick="switchTab('inventory-tab')">Inventory</button>
        </div>

        <!-- === SHOP TAB === -->
        <div class="tab-content active" id="shop-tab">
            <h2>Shop</h2>
            <div id="credits-container">
                <h2>Your Credits: <span id="user-credits">${student.credits}</span></h2>
            </div>
            <div id="avatar-options"></div>
        </div>

        <!-- === INVENTORY TAB === -->
        <div class="tab-content" id="inventory-tab">
            <h2>Your Inventory</h2>
            <div id="inventory-options">
                <p class="no-items">No items owned.</p> <!-- JS will replace this -->
            </div>
            <button id="equip-button" style="display:none;">Equip</button>
        </div>

    </div>
</div>

<!-- Category Buttons (shared across tabs) -->
<div id="category-buttons"></div>

<script>
    var username = "${student.username}";
    var selectedCategory = "${student.activeCategory}";
</script>
<script src="/javascript/avatar-shop.js"></script>

</body>
</html>
