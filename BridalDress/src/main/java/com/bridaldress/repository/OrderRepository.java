package com.bridaldress.repository;

import com.bridaldress.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Set<Order> findAllByClientId(Integer clientId);


}
