package com.mediator.demo.infrastructures.repository;

import com.mediator.demo.businesslogics.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
