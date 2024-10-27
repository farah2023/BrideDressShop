<<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/Entities/Deliverer.java
package com.agropharm.Entities;
========
package com.GREENO.domain;
>>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/domain/Deliverer.java

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "deliverers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Deliverer extends User{

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public Deliverer(){

    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
