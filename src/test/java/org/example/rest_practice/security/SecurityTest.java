package org.example.rest_practice.security;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends SecuritySetup {

    @Test
    void authentication_test() throws Exception {

        requestHelper("/api/v1/security-test/get-all", "mockUser", "12345").andExpect(status().isUnauthorized());
        requestHelper("/api/v1/security-test/get-admin", "mockUser", "12345").andExpect(status().isUnauthorized());
        requestHelper("/api/v1/security-test/get-user", "mockUser", "12345").andExpect(status().isUnauthorized());
        requestHelper("/api/v1/security-test/get-guest", "mockUser", "12345").andExpect(status().isUnauthorized());

    }

    @Test
    void authorization_test() throws Exception {

        requestHelper("/api/v1/security-test/get-all", "admin@gmail.com", "1q2w3e4r!").andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-all", "user@gmail.com", "1q2w3e4r!").andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-all", "guest@gmail.com", "1q2w3e4r!").andExpect(status().isOk());

        requestHelper("/api/v1/security-test/get-admin", "admin@gmail.com", "1q2w3e4r!").andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-admin", "user@gmail.com", "1q2w3e4r!").andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-admin", "guest@gmail.com", "1q2w3e4r!").andExpect(status().isForbidden());

        requestHelper("/api/v1/security-test/get-user", "admin@gmail.com", "1q2w3e4r!").andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-user", "user@gmail.com", "1q2w3e4r!").andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-user", "guest@gmail.com", "1q2w3e4r!").andExpect(status().isForbidden());

        requestHelper("/api/v1/security-test/get-guest", "admin@gmail.com", "1q2w3e4r!").andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-guest", "user@gmail.com", "1q2w3e4r!").andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-guest", "guest@gmail.com", "1q2w3e4r!").andExpect(status().isOk());

    }
    
}
