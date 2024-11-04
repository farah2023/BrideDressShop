package com.GREENO.service;

import com.GREENO.Entities.Address;
import com.GREENO.Entities.Admin;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.AdminRepository;
import com.GREENO.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AdminService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Admin create(RegistrationDTO registrationDTO) {
        Admin admin = new Admin();
        admin.setFirstName(registrationDTO.firstName);
        admin.setLastName(registrationDTO.lastName);
        admin.setEmail(registrationDTO.email);
        admin.setPhoneNumber(registrationDTO.phoneNumber);
        String rawPassword = registrationDTO.password;
        String hashedPassword = passwordEncoder.encode(registrationDTO.password);
        admin.setPassword(hashedPassword);
        admin.setSenior(registrationDTO.isSenior);

        Address address = new Address();
        address.setStreet(registrationDTO.street);
        address.setStreetNumber(registrationDTO.streetNumber);
        address.setCity(registrationDTO.city);
        address.setCountry(registrationDTO.country);
        address.setPostalCode(registrationDTO.postalCode);
        addressRepository.save(address);

        admin.setAddress(address);
        admin.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
        admin.setRole(roleRepository.findByName("ADMIN"));
        emailService.sendWelcomeEmail(admin.getEmail(), admin.getFirstName(), rawPassword, "ADMIN");
        return adminRepository.save(admin);
    }
    public void updateUserStatus(Integer userId, boolean isEnabled) {
        Admin admin = adminRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
        admin.setSenior(isEnabled);
        adminRepository.save(admin);
    }
}
