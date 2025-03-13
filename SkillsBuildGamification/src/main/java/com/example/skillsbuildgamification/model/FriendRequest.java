package com.example.skillsbuildgamification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /*
     * Many to One as One Student can send many FriendRequests, and receive many
     * FriendRequests, so from the FriendRequest side it is ManyToOne
     */
    @ManyToOne
    private Student sender;

    @ManyToOne
    private Student receiver;

    private FriendRequestStatus status;

    public FriendRequest() {
    }

    public FriendRequest(Student sender, Student receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendRequestStatus.PENDING; // status is always set as pending initially
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
