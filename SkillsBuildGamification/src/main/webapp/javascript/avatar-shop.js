document.addEventListener("DOMContentLoaded", function () {
    let selectedCategory = "bottts"; // Default category
    let avatarImage = document.getElementById("avatar-preview");
    let avatarContainer = document.getElementById("avatar-options");
    let categoryContainer = document.getElementById("category-buttons");

    let selectedFeatures = {}; // Store selected features dynamically

    function selectAvatarFeature(newAvatarUrl) {
        fetch(`/api/shop/equip/${getItemIdFromUrl(newAvatarUrl)}`, { method: 'POST' })
            .then(() => {
                document.getElementById("avatar-preview").src = newAvatarUrl;
            })
            .catch(error => console.error("Error updating avatar:", error));
    }

    // Extract item ID (feature) from URL
    function getItemIdFromUrl(url) {
        let params = new URLSearchParams(url.split("?")[1]);
        return params.get("feature") || ""; // Extract feature name safely
    }

    function updateAvatar() {
        let baseUrl = `https://api.dicebear.com/8.x/${selectedCategory}/svg?seed=${username}`;

        Object.keys(selectedFeatures).forEach(feature => {
            baseUrl += `&${feature}=${selectedFeatures[feature]}`;
        });

        avatarImage.src = baseUrl; // Update avatar preview
    }

    function loadAvatars(category) {
        selectedCategory = category;
        avatarContainer.innerHTML = ""; // Clear previous items

        fetch(`/api/shop/avatar/variants?username=${username}`)
            .then(response => response.json())
            .then(variants => {
                if (!variants[category]) return;

                Object.keys(variants[category]).forEach(feature => {
                    let featureContainer = document.createElement("div");
                    featureContainer.classList.add("feature-section");

                    let featureHeader = document.createElement("h3");
                    featureHeader.textContent = feature;
                    featureContainer.appendChild(featureHeader);

                    let variantContainer = document.createElement("div");
                    variantContainer.classList.add("variant-container");

                    variants[category][feature].forEach(variant => {
                        let img = document.createElement("img");
                        img.src = variant;
                        img.alt = feature;
                        img.classList.add("variant-img");

                        img.addEventListener("click", () => {
                            selectedFeatures[feature] = variant.split("seed=")[1]; // Extract feature name
                            selectAvatarFeature(variant); // Equip feature
                            updateAvatar(); // Update avatar preview
                        });

                        variantContainer.appendChild(img);
                    });

                    featureContainer.appendChild(variantContainer);
                    avatarContainer.appendChild(featureContainer);
                });
            })
            .catch(error => console.error("Error loading avatar variants:", error));
    }


    function createCategoryButtons(variants) {
        categoryContainer.innerHTML = ""; // Clear previous buttons

        let buttonContainer = document.createElement("div");
        buttonContainer.style.display = "flex";
        buttonContainer.style.justifyContent = "center";  // Ensures it stays centered
        buttonContainer.style.marginTop = "20px"; // Space from shop items

        Object.keys(variants).forEach(category => {
            let button = document.createElement("button");
            button.textContent = category.charAt(0).toUpperCase() + category.slice(1);
            button.classList.add("category-btn");

            button.addEventListener("click", () => loadAvatars(category));

            buttonContainer.appendChild(button);
        });

        categoryContainer.appendChild(buttonContainer);
    }


    fetch(`/api/shop/avatar/variants?username=${username}`)
        .then(response => response.json())
        .then(variants => {
            createCategoryButtons(variants);
            loadAvatars(selectedCategory); // Load default category
        })
        .catch(error => console.error("Error fetching categories:", error));
});