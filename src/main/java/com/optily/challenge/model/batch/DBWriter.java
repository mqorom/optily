package com.optily.challenge.model.batch;

import com.optily.challenge.model.Campaign;
import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.repository.CampaginGroupRepository;
import com.optily.challenge.repository.CampaginRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Campaign> {
    private CampaginRepository campaginRepository;
    private CampaginGroupRepository campaginGroupRepository;

    @Autowired
    public DBWriter(CampaginRepository campaginRepository, CampaginGroupRepository campaginGroupRepository) {
        this.campaginRepository = campaginRepository;
        this.campaginGroupRepository = campaginGroupRepository;
    }

    @Override
    public void write(List<? extends Campaign> campaigns) {
        CampaignGroup campaignGroup = CampaignGroup.builder().setName("Default_group").build();
        campaginGroupRepository.save(campaignGroup);
        for (Campaign campaign : campaigns) {
            campaign.setCampaignGroup(campaignGroup);
        }
        campaginRepository.saveAll(campaigns);
        System.out.println("Data Saved for campaigns: " + campaigns);
    }
}

