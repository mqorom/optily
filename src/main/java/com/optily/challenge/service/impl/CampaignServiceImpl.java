package com.optily.challenge.service.impl;

import com.optily.challenge.model.Campaign;
import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.repository.CampaginRepository;
import com.optily.challenge.service.CampaignGroupService;
import com.optily.challenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignGroupService campaignGroupService;
    private final CampaginRepository campaginRepository;

    @Autowired
    CampaignServiceImpl(CampaginRepository campaginRepository, CampaignGroupService campaignGroupService) {
        this.campaginRepository = campaginRepository;
        this.campaignGroupService = campaignGroupService;
    }

    @Override
    public List<Campaign> getAllByCampaignGroupId(Long campaignsGroupId) {
        CampaignGroup campaignGroup = campaignGroupService.getById(campaignsGroupId);
        return campaginRepository.findByCampaignGroup(campaignGroup);
    }

    @Override
    public List<Campaign> create(List<Campaign> campaigns, Long campaignGroupId) {
        CampaignGroup campaignGroup = campaignGroupService.getById(campaignGroupId);
        // TODO validate campaigns before saving them
        for (Campaign campaign : campaigns) {
            campaign.setCampaignGroup(campaignGroup);
        }
        return campaginRepository.saveAll(campaigns);
    }
}

