# 🛠️ Config Server – E-commerce Backend

Centralized configuration service for all microservices in the E-commerce backend. Fetches and distributes configuration properties from a remote Git repository.

---

## 🔧 Tech Stack

- **Java**
- **Spring Boot**
- **Spring Cloud Config Server**
- **Git (Remote Config Repo)**
- **Docker**

---

## 🚀 Features

- 📦 Centralized and externalized configuration for all microservices
- 📂 Supports multiple environments (`dev`, `prod`, etc.)
- 🔄 Dynamic reload (Spring Cloud Bus support on client-side)
- 🔐 Separation of sensitive values from application code
- 🌐 Git-backed storage for version-controlled configuration

---

## 🧱 Architecture Overview

The Config Server reads configuration files from a centralized Git repository and exposes them to all microservices at runtime. This allows consistent configuration management across environments.

📁 Config Repository:  
[ecommerce-config-repo](https://github.com/AlexisRodriguezCS/ecommerce-config-repo)

🔗 Config access URL pattern:  
`http://localhost:8888/{application}/{profile}`

All services are configured to use this server via:
```yaml
spring.config.import: configserver:
```

---

## 🧾 Example: Microservice application.yml
To connect your service to the Config Server, add the following to your application.yml:

```yaml
spring:
  application:
    name: inventory-service-dev   # This must match the config file name in the config repo
config:
  import: configserver:     # Tells Spring to fetch config from the Config Server
```
📝 Make sure your Config Server is running and points to the correct Git repo!

---

## 📁 Directory Structure

```
config-server/
├── src/
│   ├── main/
│   │   ├── java/com/example/configserver/
│   │   │   └── ConfigServerApp.java
│   └── resources/
│       └── bootstrap.yml  # Configures the server to load settings from a Git repo
├── build.gradle
└── Dockerfile
```

---

## ⚙️ Configuration

The server pulls all config files from a remote Git repository using the following setup:

```yaml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/AlexisRodriguezCS/ecommerce-config-repo
```

---


## 🧪 Testing Strategy

The config server is tested to ensure reliable startup and integration with the remote Git repository. The testing process includes:

- ✅ **Startup Check** – Verifies that the server boots successfully and connects to the Git repo.
- 🔍 **Config Fetching** – Confirms that services can retrieve the correct configuration using endpoints like `/{application}/{profile}`.
- 🧪 **Profile Resolution** – Ensures environment-specific configs (e.g., `dev`, `prod`) are loaded properly.
- 🔄 **Reload Verification** *(if using Spring Cloud Bus)* – Optionally tests that config changes in Git trigger updates in client services.

These tests mirror how configuration systems are validated in production, using integration and smoke testing techniques.

---

## 🧱 Related Services

- Infrastructure & Core Services
  - [ecommerce-infra](https://github.com/AlexisRodriguezCS/ecommerce-infra) — Infrastructure setup with Docker, CI/CD, ELK logging, Postman, and documentation
  - [ecommerce-config-repo](https://github.com/AlexisRodriguezCS/ecommerce-config-repo) — Git repo for configs
  - [ecommerce-config-server](https://github.com/AlexisRodriguezCS/ecommerce-config-server) — Centralized configuration service (this repo)
  - [ecommerce-discovery-server](https://github.com/AlexisRodriguezCS/ecommerce-discovery-server) — Eureka-based service registry
  - [ecommerce-api-gateway](https://github.com/AlexisRodriguezCS/ecommerce-api-gateway) — API gateway with routing, JWT validation, and rate limiting

- Microservices
  - [ecommerce-auth-service](https://github.com/AlexisRodriguezCS/ecommerce-auth-service) — User authentication and JWT management
  - [ecommerce-product-service](https://github.com/AlexisRodriguezCS/ecommerce-product-service) — Product catalog creation, updates, and search
  - [ecommerce-inventory-service](https://github.com/AlexisRodriguezCS/ecommerce-inventory-service) — Inventory tracking and stock adjustments
  - [ecommerce-order-service](https://github.com/AlexisRodriguezCS/ecommerce-order-service) — Order processing and checkout workflows
  - [ecommerce-payment-service](https://github.com/AlexisRodriguezCS/ecommerce-payment-service) — Secure payment processing
  - [ecommerce-notification-service](https://github.com/AlexisRodriguezCS/ecommerce-notification-service) — Email and SMS notifications for order events
  
---

## 📬 Contact

Maintained by [Alexis Rodriguez](https://github.com/AlexisRodriguezCS)
