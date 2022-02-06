package com.optily.challenge.service;


import com.optily.challenge.model.CampaignGroup;

import java.util.List;

public interface CampaignGroupService {
    List<CampaignGroup> getAll();

    CampaignGroup getById(Long id);

    CampaignGroup create(CampaignGroup campaignGroup);
}
