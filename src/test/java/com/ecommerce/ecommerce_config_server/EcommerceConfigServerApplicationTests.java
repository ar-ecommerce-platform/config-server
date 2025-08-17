package com.ecommerce.ecommerce_config_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests for the Config Server application.
 * Makes sure the application starts correctly and the context loads.
 */
@SpringBootTest(properties = {
        "spring.profiles.active=test"
})
class EcommerceConfigServerApplicationTests {

    /**
     * Check that the Spring application context starts without errors.
     * This ensures all configuration and beans work correctly.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Check that the main method runs without throwing errors.
     * This makes sure the application can start using the main method.
     */
    @Test
    void mainRunsWithoutException() {
        String[] args = {};
        assertThatCode(() -> EcommerceConfigServerApplication.main(args)).doesNotThrowAnyException();
    }
}
