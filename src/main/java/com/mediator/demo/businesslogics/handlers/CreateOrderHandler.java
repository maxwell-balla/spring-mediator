package com.mediator.demo.businesslogics.handlers;

import com.mediator.demo.businesslogics.entities.Order;
import com.mediator.demo.businesslogics.entities.OrderCreatedEvent;
import com.mediator.demo.infrastructures.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderHandler implements RequestHandler<CreateOrderCommand, Long> {

    private final OrderRepository repository;
    private final ApplicationEventPublisher publisher;

    public CreateOrderHandler(OrderRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public Class<CreateOrderCommand> requestType() {
        return CreateOrderCommand.class;
    }

    @Override
    public Long handle(CreateOrderCommand cmd) {
        Order order = new Order(cmd.product(), cmd.quantity());
        Long id = repository.save(order).getId();
        publisher.publishEvent(new OrderCreatedEvent(id));
        return id;
    }
}
