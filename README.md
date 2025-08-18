# 🛠️ Config Server – E-commerce Backend

[![CI Status](https://github.com/ar-ecommerce-platform/config-server/actions/workflows/ci-config-server.yml/badge.svg)](https://github.com/ar-ecommerce-platform/config-server/actions/workflows/ci-config-server.yml)
[![View Test Report](https://img.shields.io/badge/Test_Report-Live-blue?style=flat-square)](https://ar-ecommerce-platform.github.io/test-repo/config-server/test/)
[![View Coverage Report](https://img.shields.io/badge/Coverage_Report-Live-green?style=flat-square)](https://ar-ecommerce-platform.github.io/test-repo/config-server/coverage/)
[![View Security Report](https://img.shields.io/badge/Security_Report-Live-red?style=flat-square)](https://ar-ecommerce-platform.github.io/test-repo/config-server/security/)


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

📁 Config Repository: https://github.com/ar-ecommerce-platform/config-repo

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
│   │   ├── java/com/ecommerce/ecommerce_config_server/
│   │   │   └── EcommerceConfigServerApplication.java      # Main Spring Boot app
│   │   └── resources/
│   │       ├── application.yml                            # Optional default Spring Boot properties
│   │       └── bootstrap.yml                              # Config Server bootstrap properties
│   ├── smoke/
│   │   └── java/com/ecommerce/ecommerce_config_server/
│   │       └── ConfigServerSmokeTest.java                # Smoke test for Config Server
│   └── test/
│       ├── java/com/ecommerce/ecommerce_config_server/
│       │   └── EcommerceConfigServerApplicationTests.java  # Unit & context load tests
│       └── resources/
│           ├── application-test.yml                       # Test profile config (Spring Boot 3 format)
│           └── config-repo/
│               └── application.yml                        # Local test repo content served by Config Server
├── build.gradle
├── Dockerfile
├── .github/
│   └── workflows/
│       └── ci-config-server.yml                           # CI workflow for Config Server
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
          uri: https://github.com/ar-ecommerce-platform/config-repo
```

---


## 🧪 Testing Strategy

The `config-server` is validated with **smoke tests** to ensure reliable startup and connectivity.

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
---

## 🔗 Main Project Page

For the main E-commerce backend platform repository and all related projects, visit:

https://github.com/ar-ecommerce-platform