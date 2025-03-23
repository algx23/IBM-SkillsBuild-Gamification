package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.FriendRequest;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.FriendRequestRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.FriendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private FriendService friendService;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private FriendRequestRepository friendRequestRepository;

//    @Autowired
//    private BCryptPasswordEncoder encoder;


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
        test.setPassword("test");

        Student testReceiver = new Student();
        testReceiver.setUsername("testReceiver");
        testReceiver.setPassword("testReceiver");

        when(studentRepository.save(test)).thenReturn(test);
        when(studentRepository.save(testReceiver)).thenReturn(testReceiver);

        when(studentRepository.findByUsername("test")).thenReturn(test);
        when(studentRepository.findByUsername("testReceiver")).thenReturn(testReceiver);
    }

    @Test
    @WithMockUser(username = "test")
    public void testShowFriendsEndpoint() throws Exception {

        this.mockMvc.perform(get("https://localhost:8443/profile/test/friends")
                        .secure(true))
                .andExpect(status().isOk()) // check the status code is 200
                .andExpect(view().name("profile/friends")) // check that it returns the friends view
                .andExpect(model().attributeExists("friends")) // check that there is a model attribute called friends
                .andExpect(model().attributeExists("username")); // check that there is a model attribute called username
    }

    @Test
    @WithMockUser(username = "test")
    public void testShowFriendsRequestEndpoint() throws Exception {
        this.mockMvc.perform(get("https://localhost:8443/profile/test/friend-requests")
                        .secure(true).with(user("test")))
                .andExpect(status().isOk()) // check status code 200, the request has no errors
                .andExpect(view().name("profile/requests")) // check requests.jsp returned
                .andExpect(model().attributeExists("requests")) // check "requests" is in the model
                .andExpect(model().attributeExists("username")); // check "username" is in the model
    }

    @Test
    @WithMockUser(username = "test")
    public void testSendFriendRequestEndpoint() throws Exception {
//        mock that no request has been sent by the two users
        Student test = studentRepository.findByUsername("test");
        Student testReceiver = studentRepository.findByUsername("testReceiver");
        FriendRequest friendRequest = new FriendRequest(test, testReceiver);


        when(friendRequestRepository.save(any(FriendRequest.class))).thenReturn(friendRequest);
//        return null because the mock is that no request has been sent before
        when(friendRequestRepository.findBySenderAndReceiver(test, testReceiver)).thenReturn(null);
        when(friendRequestRepository.findByReceiverAndSender(test, testReceiver)).thenReturn(null);

        this.mockMvc.perform(post("https://localhost:8443/sendRequest/testReceiver")
                .secure(true).with(user("test")).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/test/dashboard-page"));
//        check that a friend request was actually saved
        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class));
//        there are 2 students in a friend request, so check that they are both saved
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @WithMockUser(username = "test")
    public void testAcceptRequestEndpoint() throws Exception {
//        set up a mock friendRequest
        Student test = studentRepository.findByUsername("test");
        Student testReceiver = studentRepository.findByUsername("testReceiver");
        FriendRequest friendRequest = new FriendRequest(test, testReceiver);
        friendRequest.setId(1);

//        mock that when the controller does findById it returns this friend request
        when(friendRequestRepository.findById(1)).thenReturn(friendRequest);
//        mock that when the service does save, it just returns the friend request
        when(friendRequestRepository.save(any(FriendRequest.class))).thenReturn(friendRequest);

        this.mockMvc.perform(post("https://localhost:8443/friendRequest/accept/1")
                .secure(true).with(user("test")).with(csrf()))
                .andExpect(status().is3xxRedirection())
                // should redirect to the dashboard page
                .andExpect(redirectedUrl("/test/dashboard-page"));

        //        check that the friend request does get updated , so the controller calls the service properly
        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class));
//        check that both students are saved
        verify(studentRepository, times(2)).save(any(Student.class));
    }

    @Test
    @WithMockUser(username = "test")
    public void testDeclineRequestEndpoint() throws Exception {
        Student test = studentRepository.findByUsername("test");
        Student testReceiver = studentRepository.findByUsername("testReceiver");
        FriendRequest friendRequest = new FriendRequest(test, testReceiver);
        friendRequest.setId(1);
//        mock that hwen the controller does friendRequest.findById(1) it returns this friend request
        when(friendRequestRepository.findById(1)).thenReturn(friendRequest);
        when(friendRequestRepository.save(any(FriendRequest.class))).thenReturn(friendRequest);

        this.mockMvc.perform(post("https://localhost:8443/friendRequest/decline/1")
                .secure(true).with(user("test")).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/test/dashboard-page"));
    }

    @Test
    @WithMockUser(username="test")
    public void testUnfriendEndpoint() throws Exception {
//        set up the friend request
        Student test = studentRepository.findByUsername("test");
        Student testReceiver = studentRepository.findByUsername("testReceiver");
        FriendRequest friendRequest = new FriendRequest(test, testReceiver);
        friendRequest.setId(1);
        when(friendRequestRepository.findById(1)).thenReturn(friendRequest);
        when(friendRequestRepository.save(any(FriendRequest.class))).thenReturn(friendRequest);
//        since the sender is "test" and they have sent it this should be true
        when(friendRequestRepository.findBySenderAndReceiver(test, testReceiver)).thenReturn(friendRequest);
//        since "test" is not the reciever this should return null when its called in the service
        when(friendRequestRepository.findByReceiverAndSender(test, testReceiver)).thenReturn(null);

        this.mockMvc.perform(post("https://localhost:8443/profile/test/unfriend/testReceiver")
                .with(user("test")).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/test/friends"));
    }
}
