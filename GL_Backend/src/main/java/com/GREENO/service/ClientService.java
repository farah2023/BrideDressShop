package com.GREENO.service;

import com.GREENO.domain.Address;
import com.GREENO.domain.Client;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.ClientRepository;
import com.GREENO.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Client create(RegistrationDTO registrationDTO) {
        Client client = new Client();
        client.setFirstName(registrationDTO.firstName);
        client.setLastName(registrationDTO.lastName);
        client.setEmail(registrationDTO.email);
        client.setPhoneNumber(registrationDTO.phoneNumber);
        client.setPassword(registrationDTO.password);
        client.setPenaltyPoints(0);
        client.setEnabled(true);

        Address address = new Address();
        address.setStreet(registrationDTO.street);
        address.setStreetNumber(registrationDTO.streetNumber);
        address.setCity(registrationDTO.city);
        address.setCountry(registrationDTO.country);
        address.setPostalCode(registrationDTO.postalCode);
        addressRepository.save(address);

        client.setAddress(address);
        client.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
        client.setRole(roleRepository.findByName("CLIENT"));
        return clientRepository.save(client);
    }

}
