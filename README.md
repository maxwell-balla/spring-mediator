# spring-mediator

Demonstration of the Mediator pattern (MediatR-style) with Spring Boot, using Spring's
dependency injection to route requests to their handler.

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
