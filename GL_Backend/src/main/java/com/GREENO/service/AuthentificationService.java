package com.GREENO.service;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/service/AuthentificationService.java
import com.agropharm.dto.LoginDTO;
import com.agropharm.repository.UserRepository;
=======
import com.GREENO.dto.LoginDTO;
import com.GREENO.repository.UserRepository;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/service/AuthentificationService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthentificationService {

    @Autowired
    private UserRepository userRepository;

    public String getSaltedPassword(LoginDTO loginDTO){
        return loginDTO.getPassword() + this.userRepository.findByEmail(loginDTO.getEmail()).getId().toString();
    }
}
