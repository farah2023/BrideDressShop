package com.GREENO.dto;

import com.GREENO.mapper.DTOEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO implements DTOEntity {
    public Integer id;
    public String email;
    public String firstName;
    public String lastName;
    public AddressDTO address;
    public String phoneNumber;
    public RoleDTO role;
    public Boolean isEnabled;
    public Integer penaltyPoints;

    public UserDTO(){
    }

    @JsonIgnore
    private Boolean enabled;

}
