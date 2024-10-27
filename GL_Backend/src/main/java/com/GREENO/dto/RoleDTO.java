package com.GREENO.dto;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/dto/RoleDTO.java
import com.agropharm.mapper.DTOEntity;
=======
import com.GREENO.mapper.DTOEntity;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/dto/RoleDTO.java

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
