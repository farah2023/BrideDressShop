package com.GREENO.repository;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/repository/NotificationRepository.java
import com.agropharm.Entities.Notification;
=======
import com.GREENO.domain.Notification;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/repository/NotificationRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(Integer userId);

}
