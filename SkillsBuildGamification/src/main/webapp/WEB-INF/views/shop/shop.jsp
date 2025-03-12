<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>

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

<h2>Avatar Shop</h2>
<p>Spend your points to buy avatar items!</p>

<c:forEach var="item" items="${shopItems}">
    <div class="shop-item">
        <img src="${item.imageUrl}" class="shop-item-img">
        <p>${item.name} - Cost: ${item.cost} points</p>
        <form action="/shop/purchase/${item.id}" method="post">
            <button type="submit">Buy</button>
        </form>
    </div>
</c:forEach>


</body>
</html>
