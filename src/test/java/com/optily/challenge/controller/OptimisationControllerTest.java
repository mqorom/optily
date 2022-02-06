package com.optily.challenge.controller;

import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.model.Optimisation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OptimisationControllerTest extends CommonControllerTest {
    
    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> then get empty Optimisation list")
    void getEmptyOptimisationsLists() throws Exception {
        CampaignGroup campaignGroup = saveCampaings("group1");
        mockMvc
                .perform(get("/optimisation/campaignGroup/" + campaignGroup.getId() + "?number=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Get optimizations for non existing campaignGroup")
    void allLatestOptimisationForNonExistingGroup() throws Exception {
        mockMvc
                .perform(get("/optimisation/campaignGroup/XYRR?number=5"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("When all optimisation are requested for specific group and passed number is invalid(negative, zero or greater than 5)")
    void getAllLatestOptimisationWithWrongNumberValue() throws Exception {
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/optimisation/campaignGroup/1?number=-1")));
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/optimisation/campaignGroup/1?number=0")));
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/optimisation/campaignGroup/1?number=6")));
    }


    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> create one optimisation --> then fetch it")
    void createOptimisationAndThenFetchIt() throws Exception {

        // save Campaings
        CampaignGroup campaignGroup = saveCampaings("group2");
        // create one optimisation
        Optimisation optimisation = saveOptimisation(campaignGroup.getId());

        // Retrieve Optimisations
        List<Optimisation> Optimisations = getOptimisations(campaignGroup.getId(), 5);

        assertEquals(1, Optimisations.size());
        assertEquals(optimisation, Optimisations.get(0));
    }

    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> create one optimisation --> create another optimisation --> retrieve the latest optimisation")
    void createTwoOptimisationAndThenFetchOne() throws Exception {

        // save campaings
        CampaignGroup campaignGroup = saveCampaings("group3");

        // create three optimisations
        Optimisation optimisation1 = saveOptimisation(campaignGroup.getId());
        Optimisation optimisation2 = saveOptimisation(campaignGroup.getId());

        // Retrieve Optimisations
        List<Optimisation> Optimisations = getOptimisations(campaignGroup.getId(), 1);

        assertEquals(1, Optimisations.size());
        assertEquals(optimisation2, Optimisations.get(0));
    }

    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> create 3 optimisations --> retrieve the latest 3 optimisations sorted in correct way")
    void createThreeOptimisationAndThenFetchAllOfThem() throws Exception {

        // save campaings
        CampaignGroup campaignGroup = saveCampaings("group4");

        // create three optimisations
        Optimisation optimisation1 = saveOptimisation(campaignGroup.getId());
        Optimisation optimisation2 = saveOptimisation(campaignGroup.getId());
        Optimisation optimisation3 = saveOptimisation(campaignGroup.getId());

        // Retrieve Optimisations
        List<Optimisation> Optimisations = getOptimisations(campaignGroup.getId(), 5);

        assertEquals(3, Optimisations.size());
        assertEquals(optimisation3, Optimisations.get(0));
        assertEquals(optimisation2, Optimisations.get(1));
        assertEquals(optimisation1, Optimisations.get(2));
    }

    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> create one optimisation --> accept it --> retrieve optimisations should be zero")
    void createOptimisationAndThenAcceptThenFetchReturnZeroOptimisation() throws Exception {

        // save campaings
        CampaignGroup campaignGroup = saveCampaings("group5");

        // create one optimisation
        Optimisation optimisation1 = saveOptimisation(campaignGroup.getId());

        // accept optimisation
        acceptOptimisation(optimisation1.getId());

        // Retrieve Optimisations
        List<Optimisation> Optimisations = getOptimisations(campaignGroup.getId(), 5);

        assertEquals(0, Optimisations.size());
    }

    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> create three optimisation --> accept one --> retrieve optimisations should return two")
    void createThreeOptimisationsAndThenAcceptOneThenFetchReturnTwoOptimisation() throws Exception {

        // save campaings
        CampaignGroup campaignGroup = saveCampaings("group5");

        // create one optimisation
        Optimisation optimisation1 = saveOptimisation(campaignGroup.getId());
        Optimisation optimisation2 = saveOptimisation(campaignGroup.getId());
        Optimisation optimisation3 = saveOptimisation(campaignGroup.getId());

        // accept optimisation
        acceptOptimisation(optimisation2.getId());

        // Retrieve Optimisations
        List<Optimisation> Optimisations = getOptimisations(campaignGroup.getId(), 5);

        assertEquals(2, Optimisations.size());
        assertEquals(optimisation3, Optimisations.get(0));
        assertEquals(optimisation1, Optimisations.get(1));
    }
}