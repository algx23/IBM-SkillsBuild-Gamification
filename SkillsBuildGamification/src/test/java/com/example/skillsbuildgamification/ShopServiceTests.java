package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.AvatarService;
import com.example.skillsbuildgamification.service.ShopService;
import com.example.skillsbuildgamification.utils.JsonLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTests {

    private ShopService shopService;
    private StudentRepository studentRepository;
    private AvatarService avatarService;
    private JsonLoader jsonLoader;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        avatarService = mock(AvatarService.class);
        jsonLoader = mock(JsonLoader.class);

        shopService = new ShopService();
        ReflectionTestUtils.setField(shopService, "studentRepository", studentRepository);
        ReflectionTestUtils.setField(shopService, "avatarService", avatarService);
        ReflectionTestUtils.setField(shopService, "jsonLoader", jsonLoader);}

    @Test
    void testGetAvatarVariantsLoadsSuccessfully() {
        ShopService service = new ShopService();
        Map<String, Map<String, Map<String, Object>>> result = service.getAvatarVariants();
        assertNotNull(result); // Only works if JSON file exists in test env
    }

    @Test
    void testPurchaseItem_SuccessfulPurchase() {
        Student student = new Student();
        student.setUsername("user");
        student.setCredits(50);

        when(studentRepository.findByUsername("user")).thenReturn(student);
        when(avatarService.getItemCost("bottts", "Eyes")).thenReturn(10);

        boolean result = shopService.purchaseItem("user", "bottts", "Eyes", "glow");

        assertTrue(result);
        assertEquals(40, student.getCredits());
        assertTrue(student.getOwnedItems().contains("bottts_Eyes_glow"));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testPurchaseItem_AlreadyOwned() {
        Student student = new Student();
        student.setUsername("user");
        student.setCredits(50);
        student.setOwnedItems(new ArrayList<>(List.of("bottts_Eyes_glow")));

        when(studentRepository.findByUsername("user")).thenReturn(student);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shopService.purchaseItem("user", "bottts", "Eyes", "glow")
        );
        assertEquals("Item is already owned.", exception.getMessage());
    }

    @Test
    void testPurchaseItem_InsufficientCredits() {
        Student student = new Student();
        student.setUsername("user");
        student.setCredits(5);

        when(studentRepository.findByUsername("user")).thenReturn(student);
        when(avatarService.getItemCost("bottts", "Eyes")).thenReturn(10);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                shopService.purchaseItem("user", "bottts", "Eyes", "glow")
        );
        assertEquals("Insufficient credits.", exception.getMessage());
    }

    @Test
    void testEquipItem_WhenCategoryChanges() {
        Student student = new Student();
        student.setUsername("user");
        student.setActiveCategory("bottts");
        student.setEquippedItems(new ArrayList<>(List.of("Eyes=eyes=glow")));

        when(studentRepository.findByUsername("user")).thenReturn(student);
        when(avatarService.generateAvatar(student)).thenReturn("mock-url");

        boolean result = shopService.equipItem("user", "adventurer", "Eyes", "variant1");

        assertTrue(result);
        assertEquals("adventurer", student.getActiveCategory());
        assertEquals(1, student.getEquippedItems().size());
        assertTrue(student.getEquippedItems().get(0).contains("variant1"));
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testGetOwnedItems_ReturnsCorrectData() {
        Student student = new Student();
        student.setUsername("user");
        student.setOwnedItems(new ArrayList<>(List.of("bottts_Eyes_glow")));
        student.setEquippedItems(new ArrayList<>(List.of("Eyes=eyes=glow")));

        when(studentRepository.findByUsername("user")).thenReturn(student);

        Map<String, List<String>> result = shopService.getOwnedItems("user");

        assertTrue(result.containsKey("ownedItems"));
        assertTrue(result.containsKey("equippedItems"));
        assertEquals(1, result.get("ownedItems").size());
        assertEquals(1, result.get("equippedItems").size());
    }
}
