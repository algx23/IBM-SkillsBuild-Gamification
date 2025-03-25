package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.AvatarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AvatarService avatarService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Student student = new Student();
        student.setUsername("test_user");
        student.setPassword("password");
        student.setCredits(100);
        student.setActiveCategory("bottts");
        student.setOwnedItems(new ArrayList<>());
        student.setEquippedItems(new ArrayList<>());
        studentRepository.save(student);
    }

    @Test
    @Transactional
    void fullAvatarFlowTest() throws Exception {
        // Purchase a feature
        String purchaseJson = objectMapper.writeValueAsString(
                new PurchaseRequest("bottts", "Eyes", "eyes=bulging")
        );

        mockMvc.perform(post("/api/shop/purchase")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Item purchased successfully"));

        // Equip the item
        String equipJson = objectMapper.writeValueAsString(
                new PurchaseRequest("bottts", "Eyes", "eyes=bulging")
        );

        mockMvc.perform(post("/api/shop/equip")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(equipJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Item equipped successfully"));

        // Fetch the avatar URL and check if it contains the new variant
        mockMvc.perform(get("/api/shop/avatar")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("https://api.dicebear.com")));

        // Check student object updates
        Student updated = studentRepository.findByUsername("test_user");
        assertTrue(updated.getOwnedItems().contains("bottts_Eyes_eyes=bulging"));
        assertTrue(updated.getEquippedItems().contains("Eyes=eyes=bulging"));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testEquipUnownedItem_shouldFail() throws Exception {
        String equipJson = objectMapper.writeValueAsString(
                new PurchaseRequest("bottts", "Eyes", "eyes=unownedVariant")
        );

        mockMvc.perform(post("/api/shop/equip")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(equipJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Item not owned or invalid"));
    }

    @Test
    @WithMockUser(username = "test_user")
    void testPurchaseWithInsufficientCredits_shouldFail() throws Exception {
        Student student = studentRepository.findByUsername("test_user");
        student.setCredits(0); // Set to 0 intentionally
        studentRepository.save(student);

        String purchaseJson = objectMapper.writeValueAsString(
                new PurchaseRequest("bottts", "Eyes", "eyes=tooExpensive")
        );

        mockMvc.perform(post("/api/shop/purchase")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("Insufficient credits")));
    }

    @Test
    void testUnauthenticatedAccess_shouldBeRedirected() throws Exception {
        mockMvc.perform(get("/api/shop/avatar"))
                .andExpect(status().is3xxRedirection()); // Redirect to login
    }

    @Test
    @WithMockUser(username = "test_user")
    void testAvatarUrlUpdatesOnEquip() throws Exception {
        // Purchase first
        String purchaseJson = objectMapper.writeValueAsString(
                new PurchaseRequest("bottts", "Eyes", "eyes=bulging")
        );
        mockMvc.perform(post("/api/shop/purchase")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isOk());

        // Equip it
        String equipJson = objectMapper.writeValueAsString(
                new PurchaseRequest("bottts", "Eyes", "eyes=bulging")
        );
        mockMvc.perform(post("/api/shop/equip")
                        .secure(true)
                        .with(csrf())
                        .with(user("test_user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(equipJson))
                .andExpect(status().isOk());

        // Validate the avatar URL
        Student updated = studentRepository.findByUsername("test_user");
        assertTrue(updated.getAvatarUrl().contains("eyes=bulging"));
    }

    static class PurchaseRequest {
        public String category;
        public String featureType;
        public String variant;

        public PurchaseRequest(String category, String featureType, String variant) {
            this.category = category;
            this.featureType = featureType;
            this.variant = variant;
        }
    }
}
