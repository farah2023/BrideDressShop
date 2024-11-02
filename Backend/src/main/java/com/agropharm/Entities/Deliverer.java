package com.agropharm.Entities;

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

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
