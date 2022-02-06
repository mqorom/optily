package com.optily.challenge.inventory;

import com.optily.challenge.model.Campaign;
import com.optily.challenge.model.CampaignGroup;

import java.util.ArrayList;
import java.util.List;

public class FactoryModel {

    public static CampaignGroup getCampaignGroup(String name) {
        return CampaignGroup.builder().setName(name).build();
    }

    public static Campaign getCampaign(String name, Double budget) {
        return Campaign.builder().
                setName(name).
                setBudget(budget).
                setImpressions(300.0).
                setRevenue(30.0).
                build();
    }

    public static List<Campaign> getCampaigns(Integer number, Double budget) {
        List<Campaign> campaigns = new ArrayList<>(number);
        for (int i = 0; i < number; ++i) {
            campaigns.add(getCampaign("name" + i, budget));
        }
        return campaigns;
    }
}
