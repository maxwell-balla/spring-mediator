# spring-mediator

Demonstration of the Mediator pattern (MediatR-style) with Spring Boot, using Spring's
dependency injection to route requests to their handler.

## The Mediator pattern

Mediator is one of the 23 classic design patterns catalogued by the "Gang of Four" in
*Design Patterns: Elements of Reusable Object-Oriented Software* (1994), in the
behavioral category.

**Problem it solves:** without a mediator, callers (e.g. controllers) end up depending
directly on every service that can handle their requests, and those services often end
up calling each other too. As the number of use cases grows, this produces a tangled,
many-to-many web of dependencies that's hard to test and hard to change — touching one
handler risks breaking unrelated callers.

**How Mediator fixes it:** every interaction goes through a single intermediary instead
of talking to collaborators directly. A caller sends a request object to the mediator;
the mediator looks up the one handler responsible for that request type and delegates
to it. Callers only ever know about the mediator and the request/response contracts —
never about concrete handler implementations — which turns the many-to-many web into a
simple hub-and-spoke: each handler is added or removed independently, without touching
the controllers that dispatch requests through it.

In this project, [`Mediator`](src/main/java/com/mediator/demo/infrastructures/Mediator.java)
plays that intermediary role between `OrderController` and `CreateOrderHandler`, the
same way MediatR does it in the .NET ecosystem.

## Stack

- Java 25
- Spring Boot 4.1.0
- Spring Data JPA + H2 (in-memory)
- Maven (via `mvnw`)

## Structure

Application code is split into two packages under `com.mediator.demo`:

```
src/main/java/com/mediator/demo
├── businesslogics          # framework-agnostic business logic
│   ├── Request               # marker interface for a command/query
│   ├── RequestHandler         # contract implemented by each handler
│   ├── CreateOrderCommand      # command carrying the data to handle
│   ├── CreateOrderHandler       # business logic for CreateOrderCommand
│   └── Order                      # JPA entity
└── infrastructures          # everything that exposes or wires the business logic
    ├── Mediator               # routes requests to their handler
    ├── OrderController          # REST controller
    ├── CreateOrderRequest        # HTTP request DTO
    └── OrderRepository             # Spring Data JPA repository
```

## How it works

```
HTTP POST /orders
      │
OrderController
      │
mediator.send(CreateOrderCommand)
      │
CreateOrderHandler
      │
OrderRepository.save()
      │
   H2 database
```

`Mediator` collects every `RequestHandler` bean registered in the Spring context,
indexes them by the request type they handle, then dispatches each `send(...)` call
to the matching handler.

## Running the project

```bash
./mvnw spring-boot:run
```

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"product":"widget","quantity":3}'
```
