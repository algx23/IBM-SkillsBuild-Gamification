package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.FriendRequest;
import com.example.skillsbuildgamification.model.FriendRequestStatus;
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
public class FriendsServiceTest {

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

    @Test
    public void testAcceptRequestBobToAlice() {
        Student bob = studentRepository.findByUsername("Bob");
        Student alice = studentRepository.findByUsername("Alice");
        FriendRequest testFriendRequest = new FriendRequest(bob, alice);
//        set up the request is already sent
        alice.getRequests().add(testFriendRequest);

        friendService.acceptRequest(testFriendRequest);

        assertEquals(1, alice.getRequests().size());
        assertEquals(1, alice.getFriends().size());
        assertEquals(1, bob.getFriends().size());
        assert(alice.getFriends().contains(bob));
        assert(bob.getFriends().contains(alice));
        assertEquals(FriendRequestStatus.ACCEPTED, testFriendRequest.getStatus());
    }

    @Test
    public void testAcceptRequestAliceToBob() {
            Student bob = studentRepository.findByUsername("Bob");
            Student alice = studentRepository.findByUsername("Alice");
            FriendRequest testFriendRequest = new FriendRequest(alice, bob);
//        set up the request is already sent
            bob.getRequests().add(testFriendRequest);

            friendService.acceptRequest(testFriendRequest);

            assertEquals(1, bob.getRequests().size());
            assertEquals(1, alice.getFriends().size());
            assertEquals(1, bob.getFriends().size());
            assert(alice.getFriends().contains(bob));
            assert(bob.getFriends().contains(alice));
            assertEquals(FriendRequestStatus.ACCEPTED, testFriendRequest.getStatus());
    }

    @Test
    public void testDeclineRequestBobToAlice() {
        Student bob = studentRepository.findByUsername("Bob");
        Student alice = studentRepository.findByUsername("Alice");
        FriendRequest testFriendRequest = new FriendRequest(bob, alice);
//        set up the request is already sent
        alice.getRequests().add(testFriendRequest);

        friendService.declineRequest(testFriendRequest);

        assertEquals(1, alice.getRequests().size());
        assertEquals(0, alice.getFriends().size());
        assertEquals(0, bob.getFriends().size());
        assert(!alice.getFriends().contains(bob));
        assert(!bob.getFriends().contains(alice));
        assertEquals(FriendRequestStatus.DECLINED, testFriendRequest.getStatus());
    }

    @Test
    public void testDeclineRequestAliceToBob() {
        Student bob = studentRepository.findByUsername("Bob");
        Student alice = studentRepository.findByUsername("Alice");
        FriendRequest testFriendRequest = new FriendRequest(alice, bob);
//        set up the request is already sent
        bob.getRequests().add(testFriendRequest);

        friendService.declineRequest(testFriendRequest);

        assertEquals(1, bob.getRequests().size());
        assertEquals(0, alice.getFriends().size());
        assertEquals(0, bob.getFriends().size());
        assert(!alice.getFriends().contains(bob));
        assert(!bob.getFriends().contains(alice));
        assertEquals(FriendRequestStatus.DECLINED, testFriendRequest.getStatus());
    }

    @Test // test Alice Sends to Bob, Bob accepts, and then bob unfriends
    public void testBobReceivedRequestAndUnfriends() {
        Student alice = studentRepository.findByUsername("Alice");
        Student bob = studentRepository.findByUsername("Bob");

        FriendRequest testFriendRequest = new FriendRequest(alice, bob); // bob receives request
        when(friendRequestRepository.findBySenderAndReceiver(bob, alice)).thenReturn(null); // since bob did not send
        when(friendRequestRepository.findBySenderAndReceiver(alice, bob)).thenReturn(testFriendRequest); // alic sent and bob received
        when(friendRequestRepository.findByReceiverAndSender(bob, alice)).thenReturn(testFriendRequest); // since bob did not receive it
        when(friendRequestRepository.findByReceiverAndSender(alice, bob)).thenReturn(null); // since alice did not receive it

//        set up like Bob accepted the request
        bob.getRequests().add(testFriendRequest);
        alice.getFriends().add(bob);
        bob.getFriends().add(alice);
        assertEquals(1, bob.getRequests().size());
        friendService.unfriend(bob, alice);

        assertEquals(0, bob.getRequests().size());
        assertEquals(0, alice.getRequests().size());
        assertEquals(0, bob.getFriends().size());
        assertEquals(0, alice.getFriends().size());
        assert(!alice.getFriends().contains(bob));
        assert(!bob.getFriends().contains(alice));
        verify(friendRequestRepository, times(1)).delete(any(FriendRequest.class)); // check request gets deleted
    }

    @Test // test Bob sends the request to alice, and alice accepts, and then alice unfriends
    public void testAliceReceivedRequestAndUnfriends() {
        Student alice = studentRepository.findByUsername("Alice");
        Student bob = studentRepository.findByUsername("Bob");

        FriendRequest testFriendRequest = new FriendRequest(bob, alice); // alice receives request

        when(friendRequestRepository.findBySenderAndReceiver(bob, alice)).thenReturn(testFriendRequest); // bob sent, and alice received
        when(friendRequestRepository.findBySenderAndReceiver(alice, bob)).thenReturn(null); // since alice did not send this request
        when(friendRequestRepository.findByReceiverAndSender(alice, bob)).thenReturn(testFriendRequest); // alice received and bob sent
        when(friendRequestRepository.findByReceiverAndSender(bob, alice)).thenReturn(null); // since bob was not the receiver

        alice.getRequests().add(testFriendRequest);
        alice.getFriends().add(bob);
        bob.getFriends().add(alice);
        assertEquals(1, alice.getRequests().size());
        friendService.unfriend(alice, bob);

        assertEquals(0, bob.getRequests().size());
        assertEquals(0, alice.getRequests().size());
        assertEquals(0, bob.getFriends().size());
        assertEquals(0, alice.getFriends().size());
        assert(!alice.getFriends().contains(bob));
        assert(!bob.getFriends().contains(alice));
        verify(friendRequestRepository, times(1)).delete(any(FriendRequest.class)); // check request gets deleted
    }

    @Test // test alice sends the request to bob, bob accepts, and alice unfriends
    public void testBobReceivesAndAliceUnfriends() {
        Student alice = studentRepository.findByUsername("Alice");
        Student bob = studentRepository.findByUsername("Bob");

        FriendRequest testFriendRequest = new FriendRequest(alice, bob);
        when(friendRequestRepository.findBySenderAndReceiver(alice, bob)).thenReturn(testFriendRequest);
        when(friendRequestRepository.findByReceiverAndSender(alice, bob)).thenReturn(null);

        bob.getRequests().add(testFriendRequest);
        alice.getFriends().add(bob);
        bob.getFriends().add(alice);
        assertEquals(1, bob.getRequests().size());
        friendService.unfriend(alice, bob);

        assertEquals(0, bob.getRequests().size());
        assertEquals(0, alice.getRequests().size());
        assertEquals(0, bob.getFriends().size());
        assertEquals(0, alice.getFriends().size());
        assert(!alice.getFriends().contains(bob));
        assert(!bob.getFriends().contains(alice));
        verify(friendRequestRepository, times(1)).delete(any(FriendRequest.class)); // check request gets deleted
    }


    @Test // test bob sends the request to alice, alice accepts, and then unfriends
    public void testAliceReceivesAndBobUnfriendAlice() {
        Student alice = studentRepository.findByUsername("Alice");
        Student bob = studentRepository.findByUsername("Bob");

        FriendRequest testFriendRequest = new FriendRequest(bob, alice);
        when(friendRequestRepository.findBySenderAndReceiver(bob, alice)).thenReturn(testFriendRequest);
        when(friendRequestRepository.findByReceiverAndSender(bob, alice)).thenReturn(null); // since bob did not receive it

        alice.getRequests().add(testFriendRequest);
        alice.getFriends().add(bob);
        bob.getFriends().add(alice);
        assertEquals(1, alice.getRequests().size());
        friendService.unfriend(bob, alice);

        assertEquals(0, bob.getRequests().size());
        assertEquals(0, alice.getRequests().size());
        assertEquals(0, bob.getFriends().size());
        assertEquals(0, alice.getFriends().size());
        assert(!alice.getFriends().contains(bob));
        assert(!bob.getFriends().contains(alice));
        verify(friendRequestRepository, times(1)).delete(any(FriendRequest.class)); // check request gets deleted
    }
}
