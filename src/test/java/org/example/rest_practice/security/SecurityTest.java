package org.example.rest_practice.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends SecuritySetup {

    @Test
    void authentication_test() throws Exception {

        UserDetails mockUser = User.withUsername("mock_user").password("12345").roles("MOCK_USER").build();

        requestHelper("/api/v1/security-test/get-all",mockUser).andExpect(status().isUnauthorized());
        requestHelper("/api/v1/security-test/get-admin",mockUser).andExpect(status().isUnauthorized());
        requestHelper("/api/v1/security-test/get-user",mockUser).andExpect(status().isUnauthorized());
        requestHelper("/api/v1/security-test/get-guest",mockUser).andExpect(status().isUnauthorized());
    }

    @Test
    void authorization_test() throws Exception {

        requestHelper("/api/v1/security-test/get-all", adminDetails).andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-all", userDetails).andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-all", guestDetails).andExpect(status().isOk());

        requestHelper("/api/v1/security-test/get-admin", adminDetails).andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-admin", userDetails).andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-admin", guestDetails).andExpect(status().isForbidden());

        requestHelper("/api/v1/security-test/get-user", adminDetails).andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-user", userDetails).andExpect(status().isOk());
        requestHelper("/api/v1/security-test/get-user", guestDetails).andExpect(status().isForbidden());

        requestHelper("/api/v1/security-test/get-guest", adminDetails).andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-guest", userDetails).andExpect(status().isForbidden());
        requestHelper("/api/v1/security-test/get-guest", guestDetails).andExpect(status().isOk());

    }

    private ResultActions requestHelper(String url, UserDetails userDetails) throws Exception {
        return mockMvc.perform(
                get(url)
                        .with(httpBasic(userDetails.getUsername(), userDetails.getUsername()))
        );
    }

}
