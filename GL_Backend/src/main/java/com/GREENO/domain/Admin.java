<<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/Entities/Admin.java
package com.agropharm.Entities;
========
package com.GREENO.domain;
>>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/domain/Admin.java

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User{
    @Column(name = "is_senior")
    private Boolean isSenior;

    public Admin(){

    }

    public Boolean getSenior() {
        return isSenior;
    }

    public void setSenior(Boolean senior) {
        isSenior = senior;
    }
}
