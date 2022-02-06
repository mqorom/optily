package com.optily.challenge.service.impl;

import com.optily.challenge.model.Optimisation;
import com.optily.challenge.model.Recommendation;
import com.optily.challenge.repository.RecommendationRepository;
import com.optily.challenge.service.OptimisationService;
import com.optily.challenge.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final OptimisationService optimisationService;

    @Autowired
    RecommendationServiceImpl(RecommendationRepository recommendationRepository, OptimisationService optimisationService) {
        this.recommendationRepository = recommendationRepository;
        this.optimisationService = optimisationService;
    }

    @Override
    public List<Recommendation> getLatest(Long optimizationId, Integer number) {
        Optimisation optimisation = optimisationService.GetOne(optimizationId);
        List<Recommendation> recommendations = recommendationRepository.findByOptimisation(optimisation);
        return recommendations.stream().sorted().limit(number).collect(Collectors.toList());
    }
}

