<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link rel="stylesheet" href="/css/shop.css">
</head>
<body>

<div class="shop-container">
    <!-- Left Side - Avatar Preview -->
    <div class="avatar-preview">
        <h2>Your Avatar</h2>
        <img src="${avatarUrl}" alt="Avatar" width="200">
        <c:if test="${not empty previewAvatarUrl}">
            <h3>Preview</h3>
            <img src="${previewAvatarUrl}" alt="Preview Avatar" width="200">
        </c:if>
    </div>

    <!-- Right Side - Shop Items -->
    <div class="shop-items">
        <h2>Shop</h2>
        <<c:forEach var="item" items="${shopItems}">
        <div class="shop-item">
            <img src="https://api.dicebear.com/7.x/adventurer/svg?${item.dicebearParam}" alt="${item.name}">
            <p><strong>${item.name}</strong></p>
            <p>Cost: ${item.cost} points</p>
            <p>Required Level: ${item.requiredLevel}</p>

            <form action="/shop/purchase/${item.id}" method="post">
                <button type="submit">Buy</button>
            </form>
        </div>
    </c:forEach>

    </div>
</div>

<!-- Add JavaScript to Fetch Items from API -->
<script>
    async function fetchShopItems() {
        const response = await fetch('/api/shop/items');  // Ensure correct API route
        const items = await response.json();

        let shopList = document.getElementById("shop-items");
        shopList.innerHTML = "";  // Clear previous content

        items.forEach(item => {
            shopList.innerHTML += `
            <li>
                <img src="${item.imageUrl}" alt="${item.name}" width="50">
                <strong>${item.name}</strong> - Cost: ${item.cost} points
                <form action="/shop/equip/${item.id}" method="post">
                    <button type="submit">Equip</button>
                </form>
            </li>`;
        });
    }

    window.onload = fetchShopItems;  // Load shop items when the page loads
</script>

</body>
</html>
