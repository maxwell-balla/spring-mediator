package com.mediator.demo.businesslogics.handlers;

/**
 * Business logic responsible for handling exactly one {@link Request} type.
 *
 * @param <C> the concrete request type this handler processes
 * @param <R> the type returned once the request has been handled
 */
public interface RequestHandler<C extends Request<R>, R> {

    Class<C> requestType();

    R handle(C request);
}
