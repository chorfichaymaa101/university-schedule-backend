package com.ensak.emploi.services;

import com.ensak.emploi.model.NotificationsAdmin;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.repository.NotificationsAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificationsAdminService {

    @Autowired
    private NotificationsAdminRepository notificationsAdminRepository;

    /**
     * Create a notification for a single requestId (not for all persons).
     *
     * @param requestId The ID of the request associated with the notification.
     */
    
    public void createNotificationsForRequest(Long requestId) {
        NotificationsAdmin notification = new NotificationsAdmin();

        notification.setRequestId(requestId);
        notification.setChecked(false);

        notificationsAdminRepository.save(notification);
    }
    public int countUncheckedNotifications() {
        return notificationsAdminRepository.countByCheckedFalse();
    }
    public void markAllNotificationsAsChecked() {
        List<NotificationsAdmin> uncheckedNotifications = notificationsAdminRepository.findByCheckedFalse();

        for (NotificationsAdmin notification : uncheckedNotifications) {
            notification.setChecked(true);
        }

        notificationsAdminRepository.saveAll(uncheckedNotifications);
    }
}
