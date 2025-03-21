document.addEventListener("DOMContentLoaded", function () {
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
                console.log("owned items:",ownedItems)
                console.log("equipped items:",equippedItems)
                fetchUserCredits();
                fetchAvatar(); //  avatar update on page load
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

    function buildAvatarPreviewUrl(category, previewFeature, previewVariant, allVariants) {
        let url = `https://api.dicebear.com/8.x/${category}/svg?seed=${username}`; //base url

        if (!allVariants || !allVariants[category]) { // fallback error
            console.warn("No valid features for category:", category);
            return url;
        }

        const validFeatures = Object.keys(allVariants[category]); // create a list to check features of the category

        equippedItems.forEach(item => {
            const parts = item.split("="); // breaking down Feature=feature=variant format
            if (parts.length === 3) {
                const featureName = parts[0]
                const featureKey = parts[1];
                const variant = parts[2];

                const expectedOwnedKey = `${category}_${featureName}_${featureKey}=${variant}`; // match item in owned database to verify again
                console.log(expectedOwnedKey)
                if (
                    featureName !== previewFeature && // ex: eyes should be different but rest should be same
                    validFeatures.includes(featureName) && // check if it is in valid features
                    ownedItems.includes(expectedOwnedKey) // one more layer
                ) {
                    url += `&${featureKey}=${variant}`; // add to url
                }
            }
        });

        url += `&${previewVariant}`;
        return url;
    }

    function loadAvatars(category, view = 'shop') {
        selectedCategory = category;
        const container = view === 'shop' ? avatarContainer : document.getElementById("inventory-options");
        container.innerHTML = ""; // Clear previous

        fetchAvatarVariants().then(variants => {
            if (!variants || !variants[category]) {
                console.error(`No variants found for category: ${category}`);
                return;
            }

            let hasItems = false;

            Object.entries(variants[category]).forEach(([feature, featureData]) => {
                let sectionOwnedItems = ownedItems.filter(item =>
                    item.startsWith(`${category}_${feature}_`)
                );

                // Skip rendering in inventory view if no items for this feature
                if (view === 'inventory' && sectionOwnedItems.length === 0) return;

                let featureContainer = document.createElement("div");
                featureContainer.classList.add("feature-section");

                let featureHeader = document.createElement("h3");
                featureHeader.textContent = `${feature}`;
                featureContainer.appendChild(featureHeader);

                let variantContainer = document.createElement("div");
                variantContainer.classList.add("variant-container");

                featureData.variants.forEach(variant => {
                    console.log("Preview fearture: ", feature)
                    const fullVariantUrl = buildAvatarPreviewUrl(category, feature, variant, variants);

                    // For inventory, show only owned items
                    const isOwned = ownedItems.includes(`${category}_${feature}_${variant}`);
                    const isEquipped = equippedItems.includes(`${feature}=${variant}`);
                    if (view === 'inventory' && !isOwned) return;

                    hasItems = true;

                    const img = document.createElement("img");
                    if (isOwned) {
                        img.classList.add("owned-highlight");
                    }
                    if (isEquipped) {
                        img.classList.add("equipped-highlight");
                    }
                    img.src = fullVariantUrl;
                    img.alt = feature;
                    img.classList.add("variant-img");

                    img.addEventListener("click", () => {
                        avatarImage.src = fullVariantUrl;
                        selectedItem = variant;
                        selectedFeature = feature;

                        updateActionButton(variant, feature, isOwned, featureData.cost);
                    });

                    variantContainer.appendChild(img);
                });

                featureContainer.appendChild(variantContainer);
                container.appendChild(featureContainer);
            });

            if (view === 'inventory' && !hasItems) {
                container.innerHTML = `<p class="no-items">No items owned in ${category}.</p>`;
            }
        });
    }


    function createCategoryButtons(variants) {
        categoryContainer.innerHTML = "";

        Object.keys(variants).forEach(category => {
            let button = document.createElement("button");
            button.textContent = category.charAt(0).toUpperCase() + category.slice(1);
            button.classList.add("category-btn");

            button.addEventListener("click", () => {
                loadAvatars(category, document.querySelector('.tab-btn.active').textContent.toLowerCase());
            });

            categoryContainer.appendChild(button);
        });
    }

    window.switchTab = function(tabId) {
        const tabs = document.querySelectorAll('.tab-content');
        const buttons = document.querySelectorAll('.tab-btn');

        tabs.forEach(tab => tab.classList.remove('active'));
        buttons.forEach(btn => btn.classList.remove('active'));

        document.getElementById(tabId).classList.add('active');

        if (tabId === 'shop-tab') {
            buttons[0].classList.add('active');
            loadAvatars(selectedCategory, 'shop');
        } else {
            buttons[1].classList.add('active');
            loadAvatars(selectedCategory, 'inventory');
        }
    };


    function updateActionButton(item, feature, owned, cost) {
        actionButton.style.display = "block";
        actionButton.disabled = false; // Always enable by default

        if (owned) {
            let isAlreadyEquipped = equippedItems.includes(`${feature}=${item}`);

            if (isAlreadyEquipped) {
                actionButton.textContent = "Already Equipped";
                actionButton.disabled = true; // Prevent clicking
                return;
            }

            actionButton.textContent = "Equip";

            actionButton.onclick = function () {
                fetch(`/api/shop/equip`, {
                    method: 'POST',
                    credentials: 'include',
                    headers: { 'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfToken },
                    body: JSON.stringify({ category: selectedCategory, featureType: feature, variant: item })
                })
                    .then(response => response.text().then(text => {
                        if (!response.ok) throw new Error(text);
                        return text;
                    }))
                    .then(result => {
                        alert(result);
                        fetchUserInventory().then(() => {
                            filterEquippedItems(selectedCategory);
                            fetchAvatar();
                            updateShopUI();
                        });
                    })
                    .catch(error => {
                        alert("⚠️ Error equipping item: " + error.message);
                        console.error("Error equipping item:", error);
                    });
            };
        } else {
            actionButton.textContent = `Purchase (${cost} Credits)`;
            actionButton.onclick = function () {
                fetch(`/api/shop/purchase`, {
                    method: 'POST',
                    credentials: 'include',
                    headers: { 'Content-Type': 'application/json', 'X-CSRF-TOKEN': csrfToken },
                    body: JSON.stringify({ category: selectedCategory, featureType: feature, variant: item })
                })
                    .then(response => response.text().then(text => {
                        if (!response.ok) throw new Error(text);
                        return text;
                    }))
                    .then(result => {
                        alert(result);
                        fetchUserCredits();
                        fetchUserInventory().then(() => {
                            fetchAvatar();
                            updateShopUI();
                        });
                    })
                    .catch(error => {
                        alert("⚠️ Error purchasing item: " + error.message);
                        console.error("Error purchasing item:", error);
                    });
            };
        }
    }

    function filterEquippedItems(category) {
        equippedItems = equippedItems.filter(item => {
            const featureType = item.split("=")[0]; // Extract feature type
            return ownedItems.some(owned => owned.startsWith(`${category}_${featureType}_`));
        });
    }


    function fetchAvatar() {
        fetch(`/api/shop/avatar`, {
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
                console.log("New Avatar URL:", avatarUrl); // Debugging output
                document.getElementById("avatar-preview").src = avatarUrl; // Update avatar
            })
            .catch(error => console.error("Error fetching avatar:", error));
    }


// Call this function on page load
    fetchAvatar();

    function fetchUserCredits() {
        return fetch(`/api/shop/credits`, {  // Backend API to fetch credits
            method: 'GET',
            credentials: 'include',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById("user-credits").textContent = data.credits; // Update UI
            })
            .catch(error => console.error("Error fetching user credits:", error));
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
