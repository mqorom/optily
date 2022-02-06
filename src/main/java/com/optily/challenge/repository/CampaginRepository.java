package com.optily.challenge.repository;

import com.optily.challenge.model.Campaign;
import com.optily.challenge.model.CampaignGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaginRepository extends JpaRepository<Campaign, Long> {
        List<Campaign> findByCampaignGroup(CampaignGroup campaignGroup);
}
