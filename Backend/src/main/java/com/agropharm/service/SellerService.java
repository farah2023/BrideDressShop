package com.agropharm.service;

import com.agropharm.Entities.Address;
import com.agropharm.Entities.Seller;
import com.agropharm.dto.RegistrationDTO;
import com.agropharm.repository.AddressRepository;
import com.agropharm.repository.RoleRepository;
import com.agropharm.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class SellerService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;

    public Seller create(RegistrationDTO registrationDTO) {
        Seller seller = new Seller();
        seller.setFirstName(registrationDTO.firstName);
        seller.setLastName(registrationDTO.lastName);
        seller.setEmail(registrationDTO.email);
        seller.setPhoneNumber(registrationDTO.phoneNumber);
        String rawPassword = registrationDTO.password;
        String hashedPassword = passwordEncoder.encode(registrationDTO.password);
        seller.setPassword(hashedPassword);
        seller.setEnabled(true);

        Address address = new Address();
        address.setStreet(registrationDTO.street);
        address.setStreetNumber(registrationDTO.streetNumber);
        address.setCity(registrationDTO.city);
        address.setCountry(registrationDTO.country);
        address.setPostalCode(registrationDTO.postalCode);
        addressRepository.save(address);

        seller.setAddress(address);
        seller.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
        seller.setRole(roleRepository.findByName("SELLER"));

        emailService.sendWelcomeEmail(seller.getEmail(), seller.getFirstName(), rawPassword, "SELLER");
        return sellerRepository.save(seller);
    }
    public void updateUserStatus(Integer userId, boolean isEnabled) {
        Seller seller = sellerRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        seller.setEnabled(isEnabled);
        sellerRepository.save(seller);
    }
}
