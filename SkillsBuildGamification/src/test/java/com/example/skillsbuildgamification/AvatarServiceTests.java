package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.service.AvatarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AvatarServiceTest {

    private AvatarService avatarService;

    @BeforeEach
    void setUp() {
        avatarService = new AvatarService();

        // Manually inject avatarData using reflection
        Map<String, Map<String, Map<String, Object>>> mockAvatarData = new HashMap<>();

        Map<String, Object> eyesFeature = new HashMap<>();
        eyesFeature.put("cost", 10);
        eyesFeature.put("variants", Arrays.asList("variant1", "variant2"));

        Map<String, Map<String, Object>> botttsFeatures = new HashMap<>();
        botttsFeatures.put("Eyes", eyesFeature);

        mockAvatarData.put("bottts", botttsFeatures);

        ReflectionTestUtils.setField(avatarService, "avatarData", mockAvatarData);
    }

    @Test
    void testGenerateAvatar_withValidEquippedItem() {
        Student student = new Student();
        student.setUsername("TestUser");
        student.setActiveCategory("bottts");
        student.setEquippedItems(List.of("Eyes=eyes=variant1")); // Format: FeatureName=featureType=variant

        String resultUrl = avatarService.generateAvatar(student);

        assertTrue(resultUrl.contains("eyes=variant1"));
        assertTrue(resultUrl.startsWith("https://api.dicebear.com/8.x/bottts/svg?seed=TestUser"));
    }

    @Test
    void testGenerateAvatar_withNoEquippedItems() {
        Student student = new Student();
        student.setUsername("TestUser");
        student.setActiveCategory("bottts");

        String resultUrl = avatarService.generateAvatar(student);

        assertEquals("https://api.dicebear.com/8.x/bottts/svg?seed=TestUser", resultUrl);
    }

    @Test
    void testGenerateAvatar_withMalformedEquippedItem() {
        Student student = new Student();
        student.setUsername("TestUser");
        student.setActiveCategory("bottts");
        student.setEquippedItems(List.of("Eyes=variant1")); // Only two parts

        String resultUrl = avatarService.generateAvatar(student);

        // Should fallback to base URL since item is malformed
        assertEquals("https://api.dicebear.com/8.x/bottts/svg?seed=TestUser", resultUrl);
    }

    @Test
    void testGenerateAvatar_withInvalidFeatureForCategory() {
        Student student = new Student();
        student.setUsername("TestUser");
        student.setActiveCategory("bottts");
        student.setEquippedItems(List.of("Mouth=mouth=smile")); // "Mouth" not present in mock data

        String resultUrl = avatarService.generateAvatar(student);

        // Should not include the invalid feature
        assertEquals("https://api.dicebear.com/8.x/bottts/svg?seed=TestUser", resultUrl);
    }

    @Test
    void testGetDefaultItemsForCategory_returnsDefaultVariants() {
        // Arrange
        String category = "bottts";

        // Reuse setup from @BeforeEach where we mocked "Eyes" with variants "variant1" and "variant2"
        List<String> expectedDefaults = List.of("bottts_Eyes_variant1");

        // Act
        List<String> result = avatarService.getDefaultItemsForCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDefaults.size(), result.size());
        assertTrue(result.containsAll(expectedDefaults));
    }

    @Test
    void testGetDefaultItemsForCategory_invalidCategory_returnsEmptyList() {
        // Act
        List<String> result = avatarService.getDefaultItemsForCategory("invalidCategory");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty list for invalid category");
    }


}
