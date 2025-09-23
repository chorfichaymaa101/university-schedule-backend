package com.ensak.emploi.services;

import com.ensak.emploi.model.Notification;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.repository.NotificationRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationsRepository;

    @Autowired
    private UserService personService;

    /**
     * Create notifications for all persons for a given requestId.
     *
     * @param requestId The ID of the request associated with the notification.
     */
    public void createNotificationsForAllPersons(Long requestId) {
        List<Person> allPersons = personService.getAllPersons();

        List<Notification> notifications = allPersons.stream()
                .map(person -> new Notification(
                        null,
                        person.getId(),
                        requestId,
                        false,
                        person.getRole()
                ))
                .collect(Collectors.toList());

        notificationsRepository.saveAll(notifications);
    }


    @Transactional
    public void markNotificationsAsCheckedForUser(Long userId) {
        List<Notification> uncheckedNotifications = notificationsRepository.findByUserIdAndCheckedFalse(userId);

        uncheckedNotifications.forEach(notification -> notification.setChecked(true));

        notificationsRepository.saveAll(uncheckedNotifications);
    }

    public int getUncheckedNotificationsCountForUser(Long userId) {
        return notificationsRepository.findByUserIdAndCheckedFalse(userId).size();
    }

}
