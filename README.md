# Microservices Spring Boot 3
### Overview
This repository contains the source code for a microservice-based application. Each microservice is independent, providing specific functionality, and communicates with other services using REST APIs and sessaging systems.
### Architecture
![microservices](https://github.com/user-attachments/assets/bf3bc83d-61d1-4041-b079-4fc964132dd1)
#### Description
The system follows a microservices architecture with the following services:
- Product Service: The first service that allows you to create new products and retrieve a list of all products. This service handles product details such as name, description, price.
- Inventory Service: The second service checks the stock availability of products. It provides a method to verify whether a product is in stock or not and returns a boolean value (true for in stock, false for out of stock.
- Order Service: The third service that handles order creation. It interacts with the Inventory Service by make a REST call to check the availability of products before creating an order. If the product is available, the order is saved to the database.
- API Gateway: An API Gateway with Spring CLoud Gateway that provides a single entry point to access the Product Service, Inventory Service, and Order Service. It routes incoming requests to the appropriate service and consolidates responses. The gateway simplifies communication between the client and backend services.
  Moreover, it integrates OAuth JWT Token with Keycloak for authentication and authorization, providing Single Sign-On (SSO) for users.
- API Gateway and Inventory uses Resilience4j for rate limiting and failover handling to ensure high availability and reliability. 
### The services communicate through:
- REST APIs (using HTTP/JSON)
- Message queues (Kafka/RabbitMQ)
- Database: MongoDB (Product Service), MySQL (Inventory Serivce, Order Service)
### Observation
The project contains an observability stack that combines Grafana Tempo, Loki, OpenTelemetry, and Prometheus to monitor and trace application performance, logs, and metrics. These tools work together to give you full visibility into your system, helping you track issues and optimize performance.
### Container and Deployment Testing with Docker and Kubernetes (KIND)
This project uses Docker for containerization and Kubernetes (KIND) for local deployment testing. Docker allows us to package the application and its dependencies into containers, ensuring consistency across different environments. KIND (Kubernetes in Docker) enables running lightweight Kubernetes clusters on local machines for testing deployment configurations and workflows.
