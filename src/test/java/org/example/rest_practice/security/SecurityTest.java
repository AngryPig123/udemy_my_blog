package org.example.rest_practice.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends SecuritySetup {


    //    @RequestMapping(path = "/api/v1/security-test")
    @Test
    void no_authentication_get() throws Exception {
        mockMvc.perform(
                get("/api/v1/security-test")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("admin")
    void admin_authentication_get() throws Exception {
        mockMvc.perform(
                get("/api/v1/security-test")
        ).andExpect(status().isOk());
    }

}
