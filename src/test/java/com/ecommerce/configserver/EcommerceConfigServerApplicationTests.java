package com.ecommerce.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

/** Verifies the Config Server context starts against a local (native) config source. */
@SpringBootTest
@ActiveProfiles("native")
@TestPropertySource(
    properties = {
      "spring.cloud.config.server.native.search-locations=classpath:/config-repo",
      "eureka.client.enabled=false",
      "spring.cloud.service-registry.auto-registration.enabled=false"
    })
class EcommerceConfigServerApplicationTests {

  @Test
  void contextLoads() {
    // Passes if the application context is created successfully.
  }
}
