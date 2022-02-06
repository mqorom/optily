package com.optily.challenge.controller;

import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.repository.CampaginRepository;
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
class CampaignControllerTest extends CommonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CampaginRepository repository;

    @Test
    @DisplayName("When all campaigns are requested for specific group then they are all returned")
    void allCampaignForDefaultGroup() throws Exception {

        // save 6 Campaings
        CampaignGroup campaignGroup = saveCampaings("groupX", 6);

        mockMvc
                .perform(get("/campaign?campaignGroupId=" + campaignGroup.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    @DisplayName("When all campaigns are requested for specific group that is not exist")
    void allCampaignForNonExistingGroup() throws Exception {
        mockMvc
                .perform(get("/campaign?campaignGroupId=XYZ"))
                .andExpect(status().is4xxClientError());
    }
}
