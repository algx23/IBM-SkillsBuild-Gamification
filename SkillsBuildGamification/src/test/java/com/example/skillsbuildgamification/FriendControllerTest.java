package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.FriendRequest;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.FriendRequestRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.FriendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FriendControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FriendService friendService;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Test
    void contextLoads() throws Exception {
        assert mockMvc != null;
        assert friendService != null;
        assert studentRepository != null;
    }

    @BeforeEach // set up test data that is used for each of the endpoints
    public void setUp() throws Exception {
        Student test = new Student();
        test.setUsername("test");
        test.setPassword(encoder.encode("test"));

        Student testReceiver = new Student();
        testReceiver.setUsername("testReceiver");
        testReceiver.setPassword(encoder.encode("testReceiver"));

        when(studentRepository.save(test)).thenReturn(test);
        when(studentRepository.save(testReceiver)).thenReturn(testReceiver);

        when(studentRepository.findByUsername("test")).thenReturn(test);
        when(studentRepository.findByUsername("testReceiver")).thenReturn(testReceiver);
    }

    @Test
    @WithMockUser(username = "test")
    public void testShowFriendsEndpoint() throws Exception {

        this.mockMvc.perform(get("https://localhost:8443/profile/test/friends"))
                .andExpect(status().isOk()) // check the status code is 200
                .andExpect(view().name("profile/friends")) // check that it returns the friends view
                .andExpect(model().attributeExists("friends")) // check that there is a model attribute called friends
                .andExpect(model().attributeExists("username")); // check that there is a model attribute called username
    }

    @Test
    @WithMockUser(username = "test")
    public void testShowFriendsRequestEndpoint() throws Exception {
        this.mockMvc.perform(get("https://localhost:8443/profile/test/friend-requests"))
                .andExpect(status().isOk()) // check status code 200, the request has no errors
                .andExpect(view().name("profile/requests")) // check requests.jsp returned
                .andExpect(model().attributeExists("requests")) // check "requests" is in the model
                .andExpect(model().attributeExists("username")); // check "username" is in the model

    }
}
