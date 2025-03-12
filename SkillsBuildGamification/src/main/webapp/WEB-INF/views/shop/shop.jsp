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
        <img src="${avatarUrl}" alt="Avatar">
    </div>

    <!-- Right Side - Shop Items -->
    <div class="shop-items">
        <h2>Shop</h2>
        <c:forEach var="item" items="${shopItems}">
            <div class="shop-item">
                <img src="/images/shop/${item.name}.png" alt="${item.name}">
                <p><strong>${item.name}</strong></p>
                <p>Cost: ${item.cost} points</p>
                <p>Required Level: ${item.requiredLevel}</p>

                <form action="/shop/preview/${item.id}" method="post">
                    <button type="submit">Preview</button>
                </form>

                <c:if test="${student.points >= item.cost && student.level >= item.requiredLevel}">
                    <form action="/shop/purchase/${item.id}" method="post">
                        <button type="submit">Buy</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
