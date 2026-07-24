package com.mediator.demo.infrastructures.listener;

import com.mediator.demo.businesslogics.entities.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener {

    @EventListener
    public void on(OrderCreatedEvent event) {
        // envoyer un e-mail de confirmation pour la commande event.orderId()
    }
}
