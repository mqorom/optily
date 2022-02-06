package com.optily.challenge.service;


import com.optily.challenge.model.Campaign;

import java.util.List;

public interface CampaignService {
    List<Campaign> getAllByCampaignGroupId(Long CampaignGroupId);

    List<Campaign> create(List<Campaign> campaigns, Long campaignGroupId);
}
