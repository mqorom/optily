package com.optily.challenge.service;


import com.optily.challenge.model.Optimisation;

import java.util.List;

public interface OptimisationService {
    Optimisation create(Long CampaignGroupId);

    List<Optimisation> getLatest(Long campaignGroupId, Integer number);

    Optimisation GetOne(Long optimisationId);

    void accept(Long optimisationId);
}
