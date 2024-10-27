package com.agropharm.controller;

import com.agropharm.Entities.Notification;
import com.agropharm.Entities.User;
import com.agropharm.service.NotificationService;
import com.agropharm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/notifications")
public class NotificationController {

}
