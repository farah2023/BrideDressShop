package com.bridaldress.dto;

import com.bridaldress.mapper.DTOEntity;

public class RoleDTO implements DTOEntity {
    public Integer id;
    public String name;

    public RoleDTO(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
