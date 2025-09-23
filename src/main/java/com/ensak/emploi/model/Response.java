package com.ensak.emploi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public abstract class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resId;

    private int profId;
    private int requestId;
    private String roomId;

    // No-argument constructor required by JPA
    public Response() {}

    // Constructor without resId, as it’s auto-generated
    public Response(int profId, int requestId, String roomId) {
        this.profId = profId;
        this.requestId = requestId;
        this.roomId = roomId;
    }

    // Getters and Setters
    public int getResId() { return resId; }
    public void setResId(int resId) { this.resId = resId; }

    public int getProfId() { return profId; }
    public void setProfId(int profId) { this.profId = profId; }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
}
