package com.GREENO.service;

import com.GREENO.dto.LoginDTO;
import com.GREENO.repository.UserRepository;
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
