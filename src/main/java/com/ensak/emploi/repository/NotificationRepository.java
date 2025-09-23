package com.ensak.emploi.repository;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ensak.emploi.model.Notification; // Import your entity class

import jakarta.transaction.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdAndCheckedFalse(Long userId);

  
    @Query("SELECT COUNT(n) FROM Notifications n WHERE n.checked = false AND n.userId = :userId")
    int countUncheckedNotificationsByUserId(@Param("userId") Long userId)
    ;


}
