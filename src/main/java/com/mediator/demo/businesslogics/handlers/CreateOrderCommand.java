package com.mediator.demo.businesslogics.handlers;

public record CreateOrderCommand(
        String product,
        int quantity
) implements Request<Long> {
}
