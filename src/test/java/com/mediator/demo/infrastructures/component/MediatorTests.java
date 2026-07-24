package com.mediator.demo.infrastructures.component;

import com.mediator.demo.businesslogics.handlers.Request;
import com.mediator.demo.businesslogics.handlers.RequestHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MediatorTests {

    record PingCommand(String message) implements Request<String> {
    }

    static class PingHandler implements RequestHandler<PingCommand, String> {
        @Override
        public Class<PingCommand> requestType() {
            return PingCommand.class;
        }

        @Override
        public String handle(PingCommand request) {
            return "pong:" + request.message();
        }
    }

    @Test
    void dispatchesRequestToRegisteredHandler() {
        Mediator mediator = new Mediator(List.of(new PingHandler()));

        String result = mediator.send(new PingCommand("hello"));

        assertThat(result).isEqualTo("pong:hello");
    }

    record UnhandledCommand() implements Request<Void> {
    }

    @Test
    void throwsWhenNoHandlerIsRegistered() {
        Mediator mediator = new Mediator(List.of());

        assertThatThrownBy(() -> mediator.send(new UnhandledCommand()))
                .isInstanceOf(IllegalStateException.class);
    }
}
