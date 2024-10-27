package com.GREENO.repository;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/repository/OrderRepository.java
import com.agropharm.Entities.Order;
=======
import com.GREENO.domain.Order;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/repository/OrderRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Set<Order> findAllByClientId(Integer clientId);


}
