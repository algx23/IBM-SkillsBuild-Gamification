package com.example.skillsbuildgamification.service;


import com.example.skillsbuildgamification.model.FriendRequest;
import com.example.skillsbuildgamification.model.FriendRequestStatus;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.FriendRequestRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendService {
    private final FriendRequestRepository friendRequestRepository;
    private final StudentRepository studentRepository;

    public FriendService(FriendRequestRepository friendRequestRepository, StudentRepository studentRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.studentRepository = studentRepository;
    }

    public void acceptRequest(FriendRequest friendRequest) {
        Student sender = friendRequest.getSender();
        Student receiver = friendRequest.getReceiver();

//        if the request has been accepted, add the sender to the receivers friends list and vice versa
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);

//        change the status of the friend request, and save the students with their updated friends list and save the request in the request repo
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(friendRequest);
        studentRepository.save(sender);
        studentRepository.save(receiver);
    }

    public void declineRequest(FriendRequest friendRequest) {
        Student sender = friendRequest.getSender();
        Student receiver = friendRequest.getReceiver();
        friendRequest.setStatus(FriendRequestStatus.DECLINED);
        friendRequestRepository.save(friendRequest);
        studentRepository.save(sender);
        studentRepository.save(receiver);
    }
}
