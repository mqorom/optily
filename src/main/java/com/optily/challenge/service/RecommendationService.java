package com.optily.challenge.service;


import com.optily.challenge.model.Recommendation;

import java.util.List;

public interface RecommendationService {
    List<Recommendation> getLatest(Long optimizationId, Integer number);
}
