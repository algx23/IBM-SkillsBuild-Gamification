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
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FriendsSystemTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private FriendRequestRepository friendRequestRepository;
    @InjectMocks
    private FriendService friendService;


    @BeforeEach
    public void setUp() {
        Student student1 = new Student();
        student1.setUsername("Bob");
        student1.setPassword("password");
        student1.setRequests(new ArrayList<>());

        Student student2 = new Student();
        student2.setUsername("Alice");
        student2.setPassword("password");
        student2.setRequests(new ArrayList<>());

//        mock save

        when(studentRepository.save(any(Student.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        when(friendRequestRepository.save(any(FriendRequest.class)))
                .thenAnswer(i -> i.getArguments()[0]);

//        mock friend request
        when(friendRequestRepository.findBySenderAndReceiver(student1, student2)).thenReturn(null);

        when(friendRequestRepository.findByReceiverAndSender(student1, student2)).thenReturn(null);


//        mock findByUsername
        when(studentRepository.findByUsername("Bob")).thenReturn(student1);
        when(studentRepository.findByUsername("Alice")).thenReturn(student2);
    }

    @Test
    public void contextLoads() {
    }

//    test sending friend request from Alice to Bob with no previous request
    @Test
    public void testSendRequestBobToAlice() {
        Student bob = studentRepository.findByUsername("Bob");
        Student alice = studentRepository.findByUsername("Alice");

        friendService.sendRequest(bob, alice); // student1 sender, alice receiver

        assertEquals(0, bob.getRequests().size()); // check request not added to bob (the sender)
        assertEquals(1, alice.getRequests().size()); // check request added to alice (the receiver)
        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class)); // check the friend request was saved
        verify(studentRepository).save(any(Student.class)); // check student was saved
    }

        @Test
        public void testSendRequestBobToAliceAlreadyExists() {
            Student bob = studentRepository.findByUsername("Bob");
            Student alice = studentRepository.findByUsername("Alice");

            //        set up request already exists
            FriendRequest existingRequest = new FriendRequest(bob, alice);

            when(friendRequestRepository.findBySenderAndReceiver(bob, alice)).thenReturn(existingRequest);
//            since alice did not send to bob
            when(friendRequestRepository.findByReceiverAndSender(bob, alice)).thenReturn(null);

            alice.getRequests().add(existingRequest);

            friendService.sendRequest(bob, alice);
            assertEquals(0, bob.getRequests().size());
            assertEquals(1, alice.getRequests().size());
//          check that no friend request is saved to the repository, as one already exists
            verify(friendRequestRepository, times(0)).save(any(FriendRequest.class));
    }
    @Test
    public void testSendRequestAliceToBob() {
        Student bob = studentRepository.findByUsername("Bob");
        Student alice = studentRepository.findByUsername("Alice");

        friendService.sendRequest(alice, bob); // student1 sender, alice receiver

        assertEquals(0, alice.getRequests().size()); // check request not added to alice (the sender)
        assertEquals(1, bob.getRequests().size()); // check request added to bobs (the receiver)
        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class)); // check the friend request was saved
        verify(studentRepository).save(any(Student.class)); // check student was saved
    }

    @Test
    public void testSendRequestAliceToBobAlreadyExists() {
        Student bob = studentRepository.findByUsername("Bob");
        Student alice = studentRepository.findByUsername("Alice");

        //        set up request already exists
        FriendRequest existingRequest = new FriendRequest(alice, bob);
        when(friendRequestRepository.findBySenderAndReceiver(alice, bob)).thenReturn(existingRequest);
//            since bob did not send to alice
        when(friendRequestRepository.findByReceiverAndSender(alice, bob)).thenReturn(null);

        bob.getRequests().add(existingRequest);
        friendService.sendRequest(alice, bob);
        assertEquals(alice.getRequests().size(), 0);
        assertEquals(bob.getRequests().size(), 1);
//          check that no friend request is saved to the repository, as one already exists
        verify(friendRequestRepository, times(0)).save(any(FriendRequest.class));
    }
}
