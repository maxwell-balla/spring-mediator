package com.mediator.demo.infrastructures;

import com.mediator.demo.businesslogics.Request;
import com.mediator.demo.businesslogics.RequestHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Mediator {

    private final Map<Class<?>, RequestHandler<?, ?>> handlers;

    public Mediator(List<RequestHandler<?, ?>> list) {
        handlers = list.stream().collect(Collectors.toMap(
                RequestHandler::requestType,
                Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <R, C extends Request<R>> R send(C request) {
        var handler = (RequestHandler<C, R>) handlers.get(request.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler registered for " + request.getClass());
        }
        return handler.handle(request);
    }
}
