package com.GREENO.service;

import com.GREENO.Entities.Address;
import com.GREENO.Entities.Client;
import com.GREENO.dto.RegistrationDTO;
import com.GREENO.repository.AddressRepository;
import com.GREENO.repository.ClientRepository;
import com.GREENO.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.sql.Timestamp;

@Service
public class ClientService {
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        String hashedPassword = passwordEncoder.encode(registrationDTO.password);
        client.setPassword(hashedPassword);
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
    public void updateUserStatus(Integer userId, boolean isEnabled) {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        client.setEnabled(isEnabled);
        clientRepository.save(client);
    }

}
