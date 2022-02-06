package com.optily.challenge.service.impl;

import com.optily.challenge.exception.ElementNotFoundException;
import com.optily.challenge.model.*;
import com.optily.challenge.repository.OptimisationRepository;
import com.optily.challenge.repository.RecommendationRepository;
import com.optily.challenge.service.CampaignGroupService;
import com.optily.challenge.service.CampaignService;
import com.optily.challenge.service.OptimisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptimisationServiceImpl implements OptimisationService {
    private final OptimisationRepository optimisationRepository;

    private final RecommendationRepository recommendationRepository;

    private final CampaignService campaignService;

    private final CampaignGroupService campaignGroupService;

    @Autowired
    OptimisationServiceImpl(OptimisationRepository optimisationRepository, CampaignService campaignService, RecommendationRepository recommendationRepository, CampaignGroupService campaignGroupService) {
        this.optimisationRepository = optimisationRepository;
        this.campaignService = campaignService;
        this.recommendationRepository = recommendationRepository;
        this.campaignGroupService = campaignGroupService;
    }

    @Override
    public Optimisation create(Long campaignGroupId) {
        List<Campaign> campaigns = campaignService.getAllByCampaignGroupId(campaignGroupId);
        if (campaigns.isEmpty()) {
            throw new ElementNotFoundException("Could not find campaigns associated to ID provided");
        }
        Optimisation optimisation = Optimisation.builder().setCampaignGroup(campaigns.get(0).getCampaignGroup()).setStatus(Status.CREATED).build();
        optimisationRepository.save(optimisation);

        List<Recommendation> recommendations = new ArrayList<>(campaigns.size());
        double totalBudgets = 0, totalImpressions = 0, newBudget;
        for (Campaign campaign : campaigns) {
            totalBudgets += campaign.getBudget();
            totalImpressions += campaign.getImpressions();
        }
        for (Campaign campaign : campaigns) {
            newBudget = (campaign.getImpressions() / totalImpressions) * totalBudgets;
            Recommendation recommendation = Recommendation.builder().setCampaign(campaign).setOptimisation(optimisation).setBudget(newBudget).build();
            recommendations.add(recommendation);
        }
        recommendationRepository.saveAll(recommendations);
        return optimisation;
    }

    @Override
    public List<Optimisation> getLatest(Long campaignGroupId, Integer number) {
        CampaignGroup campaignGroup = campaignGroupService.getById(campaignGroupId);
        List<Optimisation> optimisations = optimisationRepository.findByCampaignGroup(campaignGroup);
        return optimisations.stream().filter(optimisation -> optimisation.getStatus() == Status.CREATED).sorted().limit(number).collect(Collectors.toList());
    }

    @Override
    public Optimisation GetOne(Long optimisationId) {
        return optimisationRepository
                .findById(optimisationId)
                .filter(optimisation -> optimisation.getStatus() == Status.CREATED)
                .orElseThrow(() -> new ElementNotFoundException("Could not find optimisation with ID provided"));
    }

    @Override
    public void accept(Long optimisationId) {
        Optimisation optimisation = GetOne(optimisationId);
        optimisation.setStatus(Status.APPLIED);
        optimisationRepository.save(optimisation);
    }
}

