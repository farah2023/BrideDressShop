package com.GREENO.service;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/service/SellerService.java
import com.agropharm.Entities.Address;
import com.agropharm.Entities.Seller;
import com.agropharm.dto.RegistrationDTO;
import com.agropharm.repository.AddressRepository;
import com.agropharm.repository.RoleRepository;
import com.agropharm.repository.SellerRepository;
=======
import com.GREENO.domain.Address;
import com.GREENO.domain.Seller;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.RoleRepository;
import com.GREENO.repository.SellerRepository;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/service/SellerService.java
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
