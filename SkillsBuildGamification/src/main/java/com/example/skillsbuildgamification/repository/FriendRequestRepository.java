package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.FriendRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Integer> {
    public FriendRequest findById(int id);
    public List<FriendRequest> findBySender();
    public List<FriendRequest> findByReceiver();
}
