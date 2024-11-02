package com.agropharm.controller;

import com.agropharm.Entities.Client;
import com.agropharm.Entities.Deliverer;
import com.agropharm.Entities.Seller;
import com.agropharm.service.AuthentificationService;
import com.agropharm.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agropharm.Entities.User;
import com.agropharm.dto.LoginDTO;
import com.agropharm.dto.TokenDTO;
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
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            boolean isEnabled = true;
            if (user instanceof Client) {
                isEnabled = ((Client) user).getIsEnabled();
            } else if (user instanceof Seller) {
                isEnabled = ((Seller) user).getIsEnabled();
            } else if (user instanceof Deliverer) {
                isEnabled = ((Deliverer) user).getIsEnabled();
            }

            if (!isEnabled) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Account is disabled"));
            }
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new TokenDTO(jwt, expiresIn, isEnabled));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid email or password"));
        }
    }

    public class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
