# 🛠️ Config Server – E-commerce Backend



[//]: # ([![Run Integration Tests and Publish Report]&#40;https://github.com/AlexisRodriguezCS/ecommerce-config-server/actions/workflows/test.yml/badge.svg&#41;]&#40;https://github.com/AlexisRodriguezCS/ecommerce-config-server/actions/workflows/test.yml&#41; [![View Test Report]&#40;https://img.shields.io/badge/Test_Report-Live-blue?style=flat-square&#41;]&#40;https://alexisrodriguezcs.github.io/ecommerce-config-server/test-report/&#41;)

[![CI Status](https://github.com/AlexisRodriguezCS/ecommerce-config-server/actions/workflows/ci.yml/badge.svg)](https://github.com/AlexisRodriguezCS/ecommerce-config-server/actions/workflows/ci.yml)
[![View Test Report](https://img.shields.io/badge/Test_Report-Live-blue?style=flat-square)](https://alexisrodriguezcs.github.io/test-repo/config-server/test/)
[![View Coverage Report](https://img.shields.io/badge/Coverage_Report-Live-green?style=flat-square)](https://alexisrodriguezcs.github.io/test-repo/config-server/coverage/)
[![View Security Report](https://img.shields.io/badge/Security_Report-Live-red?style=flat-square)](https://alexisrodriguezcs.github.io/test-repo/config-server/security/)

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
spring:
  config:
    import: configserver:http://localhost:8888
```

---

## 🧾 Example: Microservice application.yml
To connect your service to the Config Server, add the following to your application.yml:

```yaml
spring:
  application:
    name: inventory-service-dev   # This must match the config file name in the config repo
config:
  import: configserver:http://localhost:8888    # Tells Spring to fetch config from the Config Server
```
📝 Make sure your Config Server is running and points to the correct Git repo!

---

## 📁 Directory Structure

```
config-server/
├── src/
│   ├── main/
│   │   └── java/com/ecommerce/configserver/
│   │       └── ConfigServerApp.java
│   ├── test/
│   │   └── java/com/ecommerce/ecommerce_config_server/integration/
│   │       └── ConfigServerIntegrationTest.java
│   └── resources/
│       └── bootstrap.yml  # Configures the server to load settings from a Git repo
├── build.gradle
├── Dockerfile
├── .github/
│   └── workflows/
│       └── test-report.yml
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

To ensure the `ecommerce-config-server` behaves reliably in real-world environments, the testing strategy focuses on **integration and smoke testing** techniques used in production systems.

### ✅ What We're Testing
- **Service Boot & Health** – Verifies the config server starts up and reports healthy status.
- **Git Integration** – Confirms that configs are pulled correctly from the remote Git repository.
- **Profile-Specific Resolution** – Checks that services receive the correct environment configs (e.g., `dev`, `prod`).
- **Fallback Behavior** – Ensures graceful responses for unknown profiles or services using global defaults.

### 🧠 Why This Matters
In production, configuration servers are a critical part of system startup. If they fail, services may boot with incorrect or missing properties. These tests simulate realistic interactions and edge cases to guarantee stability under normal and degraded conditions.

### ⚙️ How It's Done
- Uses **Testcontainers** to spin up a real Docker container of the config server.
- Sends real HTTP requests (via RestAssured) to endpoints like `/auth-service/dev`.
- Validates:
  - That correct YAML files are resolved and merged.
  - That the `/actuator/health` endpoint reports `UP` (smoke test).
  - That default and global fallbacks are applied as expected.

These tests reflect real-world production validation using Testcontainers-based integration testing, covering config merging, profile resolution, fallback behavior, and smoke testing.


---

## 🧱 Related Services

- **Infrastructure & Core Services**
  - [ecommerce-infra](https://github.com/AlexisRodriguezCS/ecommerce-infra) — Infrastructure setup with Docker, CI/CD, ELK logging, Postman, and documentation
  - [ecommerce-config-repo](https://github.com/AlexisRodriguezCS/ecommerce-config-repo) — Git repo for configs
  - [ecommerce-config-server](https://github.com/AlexisRodriguezCS/ecommerce-config-server) — Centralized configuration service (this repo)
  - [ecommerce-discovery-server](https://github.com/AlexisRodriguezCS/ecommerce-discovery-server) — Eureka-based service registry
  - [ecommerce-api-gateway](https://github.com/AlexisRodriguezCS/ecommerce-api-gateway) — API gateway with routing, JWT validation, and rate limiting
  - [ecommerce-test-reports](https://github.com/AlexisRodriguezCS/ecommerce-test-reports) — GitHub Pages for test, coverage, and security reports per service

- **Microservices**
  - [ecommerce-auth-service](https://github.com/AlexisRodriguezCS/ecommerce-auth-service) — User authentication and JWT management
  - [ecommerce-user-service](https://github.com/AlexisRodriguezCS/ecommerce-user-service) — User profile management and account details
  - [ecommerce-product-service](https://github.com/AlexisRodriguezCS/ecommerce-product-service) — Product catalog creation, updates, and search
  - [ecommerce-inventory-service](https://github.com/AlexisRodriguezCS/ecommerce-inventory-service) — Inventory tracking and stock adjustments
  - [ecommerce-order-service](https://github.com/AlexisRodriguezCS/ecommerce-order-service) — Order processing and checkout workflows
  - [ecommerce-payment-service](https://github.com/AlexisRodriguezCS/ecommerce-payment-service) — Secure payment processing
  - [ecommerce-notification-service](https://github.com/AlexisRodriguezCS/ecommerce-notification-service) — Email and SMS notifications for order events
---

## 📬 Contact

Maintained by [Alexis Rodriguez](https://github.com/AlexisRodriguezCS)
