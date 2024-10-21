package com.GREENO.service;

import com.GREENO.domain.Address;
import com.GREENO.domain.Seller;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.RoleRepository;
import com.GREENO.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class SellerService {
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
        seller.setPassword(registrationDTO.password);
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
        return sellerRepository.save(seller);
    }
}
