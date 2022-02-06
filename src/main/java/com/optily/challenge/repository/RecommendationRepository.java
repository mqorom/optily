package com.optily.challenge.repository;

import com.optily.challenge.model.Optimisation;
import com.optily.challenge.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByOptimisation(Optimisation optimisation);
}
