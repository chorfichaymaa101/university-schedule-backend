package com.ensak.emploi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id; 

    @Column(name = "user_id")
    private Long userId; 

    @Column(name = "request_id")
    private Long requestId; 

    @Column(name = "checked")
    private Boolean checked = false; 

    @Column(name = "role")
    private String role;

    public Notification() {
    }

    public Notification(Long notifId, Long userId, Long requestId, Boolean checked, String role) {
        this.Id = notifId;
        this.userId = userId;
        this.requestId = requestId;
        this.checked = checked;
        this.role = role;
    }

    public Long getNotifId() {
        return Id;
    }

    public void setNotifId(Long notifId) {
        this.Id = notifId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
