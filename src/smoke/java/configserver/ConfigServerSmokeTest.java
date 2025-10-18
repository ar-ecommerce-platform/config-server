package com.ecommerce.configserver;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import java.time.Duration;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Integration tests for the Config Server using Testcontainers. This test ensures the config server
 * behaves correctly under different config scenarios.
 */
@Testcontainers
@Tag("smoke")
public class ConfigServerSmokeTest {

  private static final String IMAGE_ENV_VAR = "SERVICE_IMAGE";

  private static final DockerImageName IMAGE_NAME =
      DockerImageName.parse(
          Optional.ofNullable(System.getenv(IMAGE_ENV_VAR))
              .orElseThrow(
                  () ->
                      new IllegalStateException(
                          IMAGE_ENV_VAR + " env var must be set for integration tests")));

  @SuppressWarnings("resource")
  @Container
  static GenericContainer<?> configServer =
      new GenericContainer<>(IMAGE_NAME)
          .withExposedPorts(8888)
          .waitingFor(
              Wait.forHttp("/actuator/health")
                  .forStatusCode(200)
                  .withStartupTimeout(Duration.ofMinutes(2)));

  @BeforeAll
  static void setUp() {
    // Set RestAssured's base URI to the config-server running inside the Docker container
    RestAssured.baseURI =
        "http://" + configServer.getHost() + ":" + configServer.getMappedPort(8888);
  }

  /** 🧪 Validates that default config loads correctly for auth-service. */
  @Test
  void shouldReturnAuthServiceDefaultConfig() {
    given()
        .when()
        .get("/auth-service/default")
        .then()
        .statusCode(200)
        .body("name", equalTo("auth-service"))
        .body("profiles", hasItem("default"))
        .body("propertySources[0].name", containsString("auth-service.yml"));
  }

  /** 🧪 Validates environment-specific override (dev profile). */
  @Test
  void shouldReturnAuthServiceDevConfig() {
    given()
        .when()
        .get("/auth-service/dev")
        .then()
        .statusCode(200)
        .body("name", equalTo("auth-service"))
        .body("profiles", hasItem("dev"))
        .body("propertySources[0].name", containsString("auth-service-dev.yml"))
        .body("propertySources.name", hasItem(containsString("auth-service.yml"))); // test merging
  }

  /** 🧪 Ensures graceful fallback for unknown profiles. */
  @Test
  void shouldFallbackGracefullyForInvalidProfile() {
    given()
        .when()
        .get("/auth-service/doesnotexist")
        .then()
        .statusCode(200)
        .body("name", equalTo("auth-service"))
        .body("profiles", hasItem("doesnotexist"))
        .body(
            "propertySources.name",
            hasItem(containsString("auth-service.yml"))); // fallback default
  }

  /** 🧪 Confirms global config is served for unknown services. */
  @Test
  void shouldReturnGlobalConfigForUnknownService() {
    given()
        .when()
        .get("/nonexistent-service/dev")
        .then()
        .statusCode(200)
        .body("name", equalTo("nonexistent-service"))
        .body("propertySources.name", everyItem(containsString("application.yml")));
  }

  /** 🚦 Smoke test to ensure the config server is operational and healthy. */
  @Test
  void shouldReportHealthyStatus() {
    given().when().get("/actuator/health").then().statusCode(200).body("status", equalTo("UP"));
  }
}
