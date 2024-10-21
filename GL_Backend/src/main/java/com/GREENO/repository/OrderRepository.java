package com.GREENO.repository;

import com.GREENO.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Set<Order> findAllByClientId(Integer clientId);


}
