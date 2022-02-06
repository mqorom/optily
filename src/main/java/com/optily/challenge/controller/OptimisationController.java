package com.optily.challenge.controller;

import com.optily.challenge.model.Optimisation;
import com.optily.challenge.service.OptimisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/optimisation")
@Validated
public class OptimisationController {
    private final OptimisationService optimisationService;

    @Autowired
    public OptimisationController(OptimisationService optimisationService) {
        this.optimisationService = optimisationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/campaignGroup/{campaignGroupId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Optimisation> getLatest(@PathVariable Long campaignGroupId, @RequestParam @Min(1) @Max(5) Integer number) {
        return optimisationService.getLatest(campaignGroupId, number);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Optimisation create(@RequestParam Long campaignGroupId) {
        return optimisationService.create(campaignGroupId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/accept")
    @ResponseStatus(HttpStatus.CREATED)
    public void accept(@RequestParam Long optimisationId) {
        optimisationService.accept(optimisationId);
    }
}
