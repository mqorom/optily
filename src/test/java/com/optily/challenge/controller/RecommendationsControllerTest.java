package com.optily.challenge.controller;

import com.optily.challenge.model.Campaign;
import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.model.Optimisation;
import com.optily.challenge.model.Recommendation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class RecommendationsControllerTest extends CommonControllerTest {


    @Test
    @DisplayName("Get Recommendations for non existing optimisation")
    void allRecommendationsForNonExistingOptimisation() throws Exception {
        mockMvc
                .perform(get("/recommendation/optimization/dummy?number=5"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("When all Recommendations are requested for specific optimisation and passed number is invalid(negative, zero or greater than 5)")
    void getAllLatestRecommendationsWithWrongNumberValue() throws Exception {
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/recommendation/optimization/1?number=-1")));
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/recommendation/optimization/1?number=0")));
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/recommendation/optimization/1?number=6")));
    }


    @Test
    @DisplayName("Create campaignGroup --> create campaigns --> create one optimisation --> then fetch recommendations")
    void createOptimisationAndThenFetchIt() throws Exception {

        // save Campaings
        CampaignGroup campaignGroup = saveCampaings("group3", 5, 20.0);

        // get Campaings
        List<Campaign> campaigns = getCampagins(campaignGroup.getId());

        // create one optimisation
        Optimisation optimisation = saveOptimisation(campaignGroup.getId());

        // Retrieve Recommendations
        List<Recommendation> recommendations = getRecommendations(optimisation.getId(), 5);

        assertEquals(5, recommendations.size());

        double totalBudgets = 0, totalImpressions = 0;

        for (Campaign campaign : campaigns) {
            totalBudgets += campaign.getBudget();
            totalImpressions += campaign.getImpressions();
        }
        Map<Long, Campaign> campaignMap = listToMap(campaigns);

        for (Recommendation recommendation : recommendations) {
            double expectedBudget = (campaignMap.get(recommendation.getCampaign().getId()).getImpressions() / totalImpressions) * totalBudgets;
            assertEquals(recommendation.getBudget(), expectedBudget);
        }
    }

    private Map<Long, Campaign> listToMap(List<Campaign> input) {
        Map<Long, Campaign> result = new HashMap<>(input.size());
        for (Campaign campaign : input) {
            result.put(campaign.getId(), campaign);
        }
        return result;
    }
}