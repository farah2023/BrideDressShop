package com.GREENO.service;

import com.GREENO.Entities.Address;
import com.GREENO.Entities.Deliverer;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.DelivererRepository;
import com.GREENO.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class DelivererService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DelivererRepository delivererRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;

    public Deliverer create(RegistrationDTO registrationDTO) {
        Deliverer deliverer = new Deliverer();
        deliverer.setFirstName(registrationDTO.firstName);
        deliverer.setLastName(registrationDTO.lastName);
        deliverer.setEmail(registrationDTO.email);
        deliverer.setPhoneNumber(registrationDTO.phoneNumber);
        String rawPassword = registrationDTO.password;
        String hashedPassword = passwordEncoder.encode(registrationDTO.password);
        deliverer.setPassword(hashedPassword);
        deliverer.setEnabled(true);

        Address address = new Address();
        address.setStreet(registrationDTO.street);
        address.setStreetNumber(registrationDTO.streetNumber);
        address.setCity(registrationDTO.city);
        address.setCountry(registrationDTO.country);
        address.setPostalCode(registrationDTO.postalCode);
        addressRepository.save(address);

        deliverer.setAddress(address);
        deliverer.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
        deliverer.setRole(roleRepository.findByName("DELIVERER"));
        emailService.sendWelcomeEmail(deliverer.getEmail(), deliverer.getFirstName(), rawPassword, "Deliverer");
        return delivererRepository.save(deliverer);
    }
    public void updateUserStatus(Integer userId, boolean isEnabled) {
        Deliverer deliverer = delivererRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Deliverer not found"));
        deliverer.setEnabled(isEnabled);
        delivererRepository.save(deliverer);
    }

}
