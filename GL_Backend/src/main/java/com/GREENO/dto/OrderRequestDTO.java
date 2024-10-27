<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/dto/OrderRequestDTO.java
package com.agropharm.dto;
=======
package com.GREENO.dto;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/dto/OrderRequestDTO.java

import java.util.List;

public class OrderRequestDTO {
    private List<OrderItemDTO> orderItems;
    private AddressDTO address;
    private UserDTO client;

    public OrderRequestDTO(){

    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }
}
