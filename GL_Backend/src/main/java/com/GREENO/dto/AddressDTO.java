package com.GREENO.dto;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/dto/AddressDTO.java
import com.agropharm.mapper.DTOEntity;
=======
import com.GREENO.mapper.DTOEntity;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/dto/AddressDTO.java

public class AddressDTO implements DTOEntity {
    public Integer id;
    public String street;
    public String streetNumber;
    public String city;
    public String country;
    public String postalCode;

    public AddressDTO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
