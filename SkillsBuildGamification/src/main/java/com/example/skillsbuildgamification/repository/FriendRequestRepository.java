package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.FriendRequest;
import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Integer> {

//    these methods are not used right now but may be at some point
    public FriendRequest findById(int id);
    public List<FriendRequest> findBySender(Student s);
    public List<FriendRequest> findByReceiver(Student r);
}
