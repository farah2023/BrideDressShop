package com.GREENO.repository;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/repository/RoleRepository.java
import com.agropharm.Entities.Role;
=======
import com.GREENO.domain.Role;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/repository/RoleRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
