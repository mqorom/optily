package com.optily.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optily.challenge.inventory.FactoryModel;
import com.optily.challenge.model.Campaign;
import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.model.Optimisation;
import com.optily.challenge.model.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public abstract class CommonControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;

    protected CampaignGroup saveCampaings(String campaingGroupName, Integer numberofCampaings, Double budget) throws Exception {
        CampaignGroup campaignGroupActual = FactoryModel.getCampaignGroup(campaingGroupName);
        // Save campaignGroup
        CampaignGroup campaignGroup =
                mapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                post("/campaigngroup")
                                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                        .content(mapper.writeValueAsString(campaignGroupActual))
                                                        .accept(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isCreated())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                CampaignGroup.class);


        List<Campaign> campaignsActual = FactoryModel.getCampaigns(numberofCampaings, budget);

        // Save campaigns
        mapper
                .readValue(
                        mockMvc
                                .perform(
                                        post("/campaign?campaignGroupId=" + campaignGroup.getId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(mapper.writeValueAsString(campaignsActual))
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<Campaign>>() {
                        });

        return campaignGroup;
    }

    protected CampaignGroup saveCampaings(String campaingGroupName, Integer numberofCampaings) throws Exception {
        return saveCampaings(campaingGroupName, numberofCampaings, 20.0);
    }

    protected CampaignGroup saveCampaings(String campaingGroupName) throws Exception {
        return saveCampaings(campaingGroupName, 5);
    }

    protected Optimisation saveOptimisation(Long campaignGroupId) throws Exception {
        return mapper
                .readValue(
                        mockMvc
                                .perform(
                                        post("/optimisation/create?campaignGroupId=" + campaignGroupId)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        Optimisation.class);

    }

    protected List<Optimisation> getOptimisations(Long campaignGroupId, Integer number) throws Exception {
        return mapper
                .readValue(
                        mockMvc
                                .perform(
                                        get("/optimisation/campaignGroup/" + campaignGroupId + "?number=" + number))
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<Optimisation>>() {
                        });
    }

    protected void acceptOptimisation(Long optimisationId) throws Exception {
        mockMvc
                .perform(
                        post("/optimisation/accept?optimisationId=" + optimisationId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    protected List<Recommendation> getRecommendations(Long optimizationId, Integer number) throws Exception {
        return mapper
                .readValue(
                        mockMvc
                                .perform(
                                        get("/recommendation/optimization/" + optimizationId + "?number=" + number))
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<Recommendation>>() {
                        });
    }

    protected List<Campaign> getCampagins(Long campaignGroupId) throws Exception {
        return mapper
                .readValue(
                        mockMvc
                                .perform(
                                        get("/campaign?campaignGroupId=" + campaignGroupId))
                                .andExpect(status().is2xxSuccessful())
                                .andReturn()
                                .getResponse()
                                .getContentAsString(),
                        new TypeReference<List<Campaign>>() {
                        });
    }
}
