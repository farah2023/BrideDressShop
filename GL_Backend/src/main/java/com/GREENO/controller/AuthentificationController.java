package com.GREENO.controller;

import com.GREENO.service.AuthentificationService;
import com.GREENO.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.GREENO.domain.User;
import com.GREENO.dto.LoginDTO;
import com.GREENO.dto.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "api/auth")
public class AuthentificationController {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {

        String saltedPassword = authentificationService.getSaltedPassword(loginDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new TokenDTO(jwt, expiresIn));
    }

    // prilikom registracije koristi String hashedPassword = passwordEncoder.encode(rawPassword);
}
