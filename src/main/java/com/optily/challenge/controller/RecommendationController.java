package com.optily.challenge.controller;

import com.optily.challenge.model.Recommendation;
import com.optily.challenge.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
@Validated
public class RecommendationController {
    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/optimization/{optimizationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Recommendation> getLatestRecommendations(@PathVariable Long optimizationId, @RequestParam @Min(1) @Max(5) Integer number) {
        return recommendationService.getLatest(optimizationId, number);
    }
}
