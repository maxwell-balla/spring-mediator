# spring-mediator

Démonstration du pattern Mediator (à la MediatR) avec Spring Boot 4, en s'appuyant sur
l'injection de dépendances de Spring pour router les requêtes vers leur handler.

## Stack

- Java 25
- Spring Boot 4.1.0
- Spring Data JPA + H2 (in-memory)
- Maven (via `mvnw`)

## Structure

Le code applicatif est séparé en deux packages sous `com.mediator.demo` :

```
src/main/java/com/mediator/demo
├── businesslogics          # logique métier, indépendante du framework web/persistence
│   ├── handlers             # Request / RequestHandler / commandes / handlers
│   └── entities              # entités JPA et événements de domaine
└── infrastructures          # tout ce qui expose ou câble la logique métier
    ├── component              # le Mediator (routage des requêtes vers les handlers)
    ├── controller              # contrôleurs REST
    ├── dto                       # objets d'entrée/sortie HTTP
    ├── repository              # accès aux données (Spring Data JPA)
    └── listener                  # écouteurs d'événements Spring
```

## Fonctionnement

```
HTTP POST /orders
      │
OrderController
      │
mediator.send(CreateOrderCommand)
      │
CreateOrderHandler
      │
OrderRepository.save()  ──► publisher.publishEvent(OrderCreatedEvent)
      │                                │
  Base H2                      SendEmailListener
```

Le `Mediator` reçoit tous les `RequestHandler` déclarés comme beans Spring, les indexe
par type de requête, puis délègue chaque `send(...)` au handler correspondant.

## Lancer le projet

```bash
./mvnw spring-boot:run
```

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"product":"widget","quantity":3}'
```

## Tests

```bash
./mvnw test
```
