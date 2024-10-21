package com.GREENO.service;

import com.GREENO.domain.Address;
import com.GREENO.domain.Deliverer;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.DelivererRepository;
import com.GREENO.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class DelivererService {
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
        deliverer.setPassword(registrationDTO.password);
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
        return delivererRepository.save(deliverer);
    }

}
