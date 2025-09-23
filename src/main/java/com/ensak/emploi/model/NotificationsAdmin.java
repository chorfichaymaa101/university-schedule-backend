package com.ensak.emploi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "notifications_admin")
public class NotificationsAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id; 

    @Column(name = "request_id")
    private Long requestId; 

    @Column(name = "checked")
    private Boolean checked = false; 

    public NotificationsAdmin() {
    }


    public NotificationsAdmin(Long requestId, Boolean checked) {
        this.requestId = requestId;
        this.checked = checked;
}

    public NotificationsAdmin(Long notif_id, Long requestId, Boolean checked) {
        this.Id = notif_id;
        this.requestId = requestId;
        this.checked = checked;
    }

    // Getters and setters
    public Long getNotifId() {
        return Id;
    }

    public void setNotifId(Long notifId) {
        this.Id = notifId;
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
}
