package com.ensak.emploi.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ensak.emploi.model.NotificationsAdmin; // Import your entity class


@Repository
public interface NotificationsAdminRepository extends JpaRepository<NotificationsAdmin, Long> {
    int countByCheckedFalse();
    List<NotificationsAdmin> findByCheckedFalse();

}
