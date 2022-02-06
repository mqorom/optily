package com.optily.challenge.controller;

import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.service.CampaignGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigngroup")
public class CampaignGroupController {

    private final CampaignGroupService campaignGroupService;

    @Autowired
    public CampaignGroupController(CampaignGroupService campaignGroupService) {
        this.campaignGroupService = campaignGroupService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CampaignGroup> getAll() {
        return campaignGroupService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignGroup create(@RequestBody CampaignGroup campaignGroup) {
        return campaignGroupService.create(campaignGroup);
    }
}