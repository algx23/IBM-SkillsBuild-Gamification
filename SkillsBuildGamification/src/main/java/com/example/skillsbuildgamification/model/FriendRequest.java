package com.example.skillsbuildgamification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class FriendRequest {
    @Id
    private int id;
    @OneToOne
    private Student sender;
    @OneToOne
    private Student receiver;
    private FriendRequestStatus status;

    public FriendRequest() {}

    public FriendRequest(Student sender, Student receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendRequestStatus.PENDING; // status is always set as pending initially
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public void setSender(Student sender) {
        this.sender = sender;
    }

    public Student getSender() {
        return sender;
    }

    public void setReceiver(Student receiver) {
        this.receiver = receiver;
    }

    public Student getReceiver() {
        return receiver;
    }


}
