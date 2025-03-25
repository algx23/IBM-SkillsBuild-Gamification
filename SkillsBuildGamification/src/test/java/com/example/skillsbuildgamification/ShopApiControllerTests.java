package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShopApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShopService shopService;

    @MockitoBean
    private StudentRepository studentRepository;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setUsername("test_user");
        testStudent.setCredits(50);
        testStudent.setEquippedItems(new ArrayList<>(List.of("Eyes=eyes=variant1")));
        testStudent.setOwnedItems(new ArrayList<>(List.of("bottts_Eyes_eyes=variant1")));

        when(studentRepository.findByUsername("test_user")).thenReturn(testStudent);
    }

    @Test
    @WithMockUser(username = "test_user")
    void testGetOwnedItemsEndpoint() throws Exception {
        Map<String, List<String>> inventory = new HashMap<>();
        inventory.put("ownedItems", testStudent.getOwnedItems());
        inventory.put("equippedItems", testStudent.getEquippedItems());

        when(shopService.getOwnedItems("test_user")).thenReturn(inventory);

        mockMvc.perform(get("/api/shop/owned").secure(true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownedItems").isArray())
                .andExpect(jsonPath("$.equippedItems").isArray());
    }

    @Test
    @WithMockUser(username = "test_user")
    void testGetStudentAvatarEndpoint() throws Exception {
        testStudent.setAvatarUrl("https://api.dicebear.com/8.x/bottts/svg?seed=test_user");

        mockMvc.perform(get("/api/shop/avatar").secure(true))
                .andExpect(status().isOk())
                .andExpect(content().string(testStudent.getAvatarUrl()));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testGetCreditsEndpoint() throws Exception {
        mockMvc.perform(get("/api/shop/credits").secure(true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.credits").value(50));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testGetAvatarVariantsEndpoint() throws Exception {
        Map<String, Map<String, Map<String, Object>>> variants = new HashMap<>();

        Map<String, Object> eyesData = new HashMap<>();
        eyesData.put("cost", 10);
        eyesData.put("variants", List.of("eyes=variant1", "eyes=variant2"));

        Map<String, Map<String, Object>> features = new HashMap<>();
        features.put("Eyes", eyesData);

        variants.put("bottts", features);

        when(shopService.getAvatarVariants()).thenReturn(variants);

        mockMvc.perform(get("/api/shop/avatar/variants").secure(true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bottts.Eyes.cost").value(10));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testEquipItemEndpoint() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("category", "bottts");
        request.put("featureType", "Eyes");
        request.put("variant", "variant1");

        when(shopService.equipItem("test_user", "bottts", "Eyes", "variant1")).thenReturn(true);

        mockMvc.perform(post("/api/shop/equip")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Item equipped successfully"));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testPurchaseItemEndpoint_success() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("category", "bottts");
        request.put("featureType", "Eyes");
        request.put("variant", "variant2");

        when(shopService.purchaseItem("test_user", "bottts", "Eyes", "variant2")).thenReturn(true); // ✅ Fixed here

        mockMvc.perform(post("/api/shop/purchase")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Item purchased successfully"));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testPurchaseItemEndpoint_failure() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("category", "bottts");
        request.put("featureType", "Eyes");
        request.put("variant", "variant3");

        when(shopService.purchaseItem("test_user", "bottts", "Eyes", "variant3"))
                .thenThrow(new RuntimeException("Insufficient credits."));

        mockMvc.perform(post("/api/shop/purchase")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient credits."));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testEquipItemEndpoint_failure() throws Exception {
        Map<String, String> request = Map.of(
                "category", "bottts",
                "featureType", "Eyes",
                "variant", "variant_invalid"
        );

        when(shopService.equipItem("test_user", "bottts", "Eyes", "variant_invalid")).thenReturn(false);

        mockMvc.perform(post("/api/shop/equip")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Item not owned or invalid"));
    }

    @Test
    @WithMockUser(username = "ghost_user")
    void testGetStudentAvatarEndpoint_notFound() throws Exception {
        when(studentRepository.findByUsername("ghost_user")).thenReturn(null);

        mockMvc.perform(get("/api/shop/avatar")
                        .secure(true)
                        .with(user("ghost_user")))
                .andExpect(status().isNotFound());
    }


}
