package com.mediator.demo.infrastructures.controller;

import com.mediator.demo.businesslogics.handlers.CreateOrderCommand;
import com.mediator.demo.infrastructures.component.Mediator;
import com.mediator.demo.infrastructures.dto.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final Mediator mediator;

    public OrderController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/orders")
    public Long create(@Valid @RequestBody CreateOrderRequest dto) {
        return mediator.send(new CreateOrderCommand(dto.product(), dto.quantity()));
    }
}
