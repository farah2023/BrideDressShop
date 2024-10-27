<<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/Entities/Client.java
package com.agropharm.Entities;
========
package com.GREENO.domain;
>>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/domain/Client.java

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Client extends User{

    @Column(name = "penalty_points")
    private int penaltyPoints;
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public Client(){

    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        int penaltyPointsSum = getPenaltyPoints();
        penaltyPointsSum += penaltyPoints;
        this.penaltyPoints = penaltyPointsSum;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
