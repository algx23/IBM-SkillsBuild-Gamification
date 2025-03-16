document.addEventListener("DOMContentLoaded", function () {
    let selectedCategory = "bottts"; // Default category
    let avatarImage = document.getElementById("avatar-preview");
    let avatarContainer = document.getElementById("avatar-options");
    let categoryContainer = document.getElementById("category-buttons");

    let selectedFeatures = {}; // Store selected features dynamically

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
                    let featureHeader = document.createElement("h3");
                    featureHeader.textContent = feature;
                    avatarContainer.appendChild(featureHeader);

                    let featureContainer = document.createElement("div");
                    featureContainer.style.display = "flex";
                    featureContainer.style.flexWrap = "wrap";

                    variants[category][feature].forEach(variant => {
                        let img = document.createElement("img");
                        img.src = variant;
                        img.alt = feature;
                        img.style = "width: 80px; height: 80px; margin: 5px; cursor: pointer; border: 1px solid #ddd;";

                        img.addEventListener("click", () => {
                            selectedFeatures[feature] = variant.split("seed=")[1]; // Extract feature name
                            updateAvatar(); // Update avatar preview
                        });

                        featureContainer.appendChild(img);
                    });

                    avatarContainer.appendChild(featureContainer);
                });
            })
            .catch(error => console.error("Error loading avatar variants:", error));
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

    fetch(`/api/shop/avatar/variants?username=${username}`)
        .then(response => response.json())
        .then(variants => {
            createCategoryButtons(variants);
            loadAvatars(selectedCategory); // Load default category
        })
        .catch(error => console.error("Error fetching categories:", error));
});
