package com.GREENO.dto;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/dto/UserDTO.java
import com.agropharm.mapper.DTOEntity;
=======
import com.GREENO.mapper.DTOEntity;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/dto/UserDTO.java

public class UserDTO implements DTOEntity {
    public Integer id;
    public String email;
    public String firstName;
    public String lastName;
    public AddressDTO address;
    public String phoneNumber;
    public RoleDTO role;


    public UserDTO(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
