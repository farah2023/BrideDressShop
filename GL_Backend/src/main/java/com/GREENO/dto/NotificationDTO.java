package com.GREENO.dto;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/dto/NotificationDTO.java
import com.agropharm.mapper.DTOEntity;
=======
import com.GREENO.mapper.DTOEntity;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/dto/NotificationDTO.java
import java.sql.Timestamp;

public class NotificationDTO implements DTOEntity {
    public Integer id;
    public String title;
    public String content;
    public boolean isRead;
    public Timestamp createdAt;
    public UserDTO user;

    public NotificationDTO(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
