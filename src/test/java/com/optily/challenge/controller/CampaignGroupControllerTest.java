package com.optily.challenge.controller;

import com.optily.challenge.repository.CampaginGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class CampaignGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CampaginGroupRepository repository;

    @Test
    @DisplayName("When all campaign groups are requested then they are all returned")
    void allCampaignGroups() throws Exception {
        mockMvc
                .perform(get("/campaigngroup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize((int) repository.count())));
    }
}
