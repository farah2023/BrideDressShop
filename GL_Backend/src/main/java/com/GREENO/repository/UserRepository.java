package com.GREENO.repository;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/repository/UserRepository.java
import com.agropharm.Entities.User;
=======
import com.GREENO.domain.User;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/repository/UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findByRoleName(String roleName);

}
