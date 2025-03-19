document.addEventListener("DOMContentLoaded", function () {
    let selectedCategory = "bottts"; // Default category
    let avatarImage = document.getElementById("avatar-preview");
    let avatarContainer = document.getElementById("avatar-options");
    let categoryContainer = document.getElementById("category-buttons");

    let csrfToken = document.getElementById("csrfToken").value; // Fetch CSRF Token
    let actionButton = document.createElement("button"); // Single dynamic button
    actionButton.id = "avatar-action-button";
    actionButton.style.display = "none"; // Initially hidden
    document.body.appendChild(actionButton);

    let selectedItem = null;
    let selectedFeature = null;
    let ownedItems = [];
    let equippedItems = [];

    console.log("Username in JS:", username); // Debug username

    function fetchUserInventory() {
        return fetch(`/api/shop/owned`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(response => response.json())
            .then(data => {
                ownedItems = data.ownedItems || [];
                equippedItems = data.equippedItems || [];

                fetchAvatar(); // ✅ **Force avatar update on page load**
            })
            .catch(error => console.error("Error fetching user items:", error));
    }



    function fetchAvatarVariants() {
        return fetch(`/api/shop/avatar/variants`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(response => response.json())
            .catch(error => console.error("Error loading avatar variants:", error));
    }

    function loadAvatars(category) {
        selectedCategory = category;
        avatarContainer.innerHTML = ""; // Clear previous items

        fetchAvatarVariants().then(variants => {
            if (!variants || !variants[category]) {
                console.error(`No variants found for category: ${category}`);
                return;
            }

            Object.entries(variants[category]).forEach(([feature, featureData]) => {
                let featureContainer = document.createElement("div");
                featureContainer.classList.add("feature-section");

                let featureHeader = document.createElement("h3");
                featureHeader.textContent = `${feature} - Cost: ${featureData.cost} Credits`; // ✅ **Fix cost display**
                featureContainer.appendChild(featureHeader);

                let variantContainer = document.createElement("div");
                variantContainer.classList.add("variant-container");

                featureData.variants.forEach(variant => {
                    let fullVariantUrl = `https://api.dicebear.com/8.x/${category}/svg?seed=${username}&${variant}`;

                    let img = document.createElement("img");
                    img.src = fullVariantUrl;
                    img.alt = feature;
                    img.classList.add("variant-img");

                    img.addEventListener("click", () => {
                        avatarImage.src = fullVariantUrl;
                        selectedItem = variant;
                        selectedFeature = feature;

                        let owned = ownedItems.includes(`${selectedCategory}_${selectedFeature}_${selectedItem}`);
                        updateActionButton(selectedItem, selectedFeature, owned, featureData.cost); // ✅ **Fix cost**
                    });

                    variantContainer.appendChild(img);
                });

                featureContainer.appendChild(variantContainer);
                avatarContainer.appendChild(featureContainer);
            });
        });
    }


    function createCategoryButtons(variants) {
        categoryContainer.innerHTML = "";

        Object.keys(variants).forEach(category => {
            let button = document.createElement("button");
            button.textContent = category.charAt(0).toUpperCase() + category.slice(1);
            button.classList.add("category-btn");

            button.addEventListener("click", () => loadAvatars(category));

            categoryContainer.appendChild(button);
        });
    }

    function updateActionButton(item, feature, owned, cost) {
        actionButton.style.display = "block";

        if (owned) {
            actionButton.textContent = "Equip";
            actionButton.disabled = false;

            actionButton.onclick = function () {
                fetch(`/api/shop/equip`, {
                    method: 'POST',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': csrfToken
                    },
                    body: JSON.stringify({
                        category: selectedCategory,
                        featureType: feature,
                        variant: item
                    })
                })
                    .then(response => response.text())
                    .then(result => {
                        alert(result);

                        // ✅ Ensure the equipped item persists
                        fetchUserInventory().then(() => {
                            fetchAvatar();  // ✅ Reload avatar from backend
                            updateShopUI();
                        });
                    })
                    .catch(error => console.error("Error equipping item:", error));
            };
            } else {
            actionButton.textContent = `Purchase (${cost} Credits)`;
            actionButton.onclick = function () {
                fetch(`/api/shop/purchase`, {
                    method: 'POST',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': csrfToken
                    },
                    body: JSON.stringify({
                        category: selectedCategory,
                        featureType: feature,
                        variant: item
                    })
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Purchase failed: " + response.status);
                        }
                        return response.text();
                    })
                    .then(result => {
                        alert(result);
                        fetchAvatar(); // ✅ **Ensure avatar updates immediately**
                        updateShopUI();
                    })
                    .catch(error => console.error("Error purchasing item:", error));
            };
        }
    }

    function fetchAvatar() {
        fetch(`/api/shop/avatar`, { // ✅ Fix: Correct endpoint
            method: 'GET',
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Avatar fetch failed");
                }
                return response.text();
            })
            .then(avatarUrl => {
                document.getElementById("avatar-preview").src = avatarUrl; // ✅ Update avatar
            })
            .catch(error => console.error("Error fetching avatar:", error));
    }

// Call this function on page load
    fetchAvatar();

    function updateAvatarUI() {
        let avatarUrl = `https://api.dicebear.com/8.x/bottts/svg?seed=${username}`;

        equippedItems.forEach(item => {
            avatarUrl += `&${item}`;
        });

        avatarImage.src = avatarUrl; // Ensure avatar updates on page load
    }

    function updateShopUI() {
        fetchUserInventory().then(() => {
            loadAvatars(selectedCategory);
        });
    }

    fetchUserInventory().then(() => {
        fetchAvatarVariants().then(variants => {
            createCategoryButtons(variants);
            loadAvatars(selectedCategory);
        });
    });
});
