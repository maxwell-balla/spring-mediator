package com.mediator.demo.businesslogics;

public record CreateOrderCommand(
        String product,
        int quantity
) implements Request<Long> {
}
