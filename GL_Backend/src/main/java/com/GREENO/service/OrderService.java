package com.GREENO.service;

import com.GREENO.domain.*;
import com.GREENO.domain.enums.OrderStatus;
import com.GREENO.dto.OrderItemDTO;
import com.GREENO.dto.OrderRequestDTO;
import com.GREENO.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public Set<Order> getAll(){
        return new HashSet<>(orderRepository.findAll());
    }


}
