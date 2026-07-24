package com.mediator.demo.businesslogics;

import com.mediator.demo.infrastructures.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderHandler implements RequestHandler<CreateOrderCommand, Long> {

    private final OrderRepository repository;

    public CreateOrderHandler(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<CreateOrderCommand> requestType() {
        return CreateOrderCommand.class;
    }

    @Override
    public Long handle(CreateOrderCommand cmd) {
        Order order = new Order(cmd.product(), cmd.quantity());
        return repository.save(order).getId();
    }
}
