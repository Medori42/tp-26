# Microservices Observability & Resilience

This project demonstrates a robust microservices architecture focused on observability and resilience, featuring Inventory and Pricing management services.

## Developed By
- **Medori42** (GitHub: [Medori42](https://github.com/Medori42))

## Features
- **Resilience**: Implements Circuit Breaker and Retry patterns using Resilience4j.
- **Database Architecture**: Uses MySQL for persistent storage with JPA/Hibernate.
- **Containerization**: Fully Dockerized environment using Docker Compose.
- **Observability**: Spring Boot Actuator integration for health monitoring.

## System Architecture
- **Inventory Management Service**: Manages book stocks and provides borrow functionality.
- **Pricing Management Service**: Dynamically calculates prices for items.

## Getting Started
### Prerequisites
- Java 17+
- Maven
- Docker & Docker Compose

### Deployment
```bash
docker-compose up --build
```

## License
MIT License - Created for educational purposes.
