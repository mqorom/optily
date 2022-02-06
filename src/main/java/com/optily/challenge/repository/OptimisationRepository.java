package com.optily.challenge.repository;

import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.model.Optimisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptimisationRepository extends JpaRepository<Optimisation, Long> {
    List<Optimisation> findByCampaignGroup(CampaignGroup campaignGroup);
}
