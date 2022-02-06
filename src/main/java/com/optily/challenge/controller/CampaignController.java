package com.optily.challenge.controller;

import com.optily.challenge.model.Campaign;
import com.optily.challenge.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Campaign> getAllByCampaignGroupId(@RequestParam Long campaignGroupId) {
        return campaignService.getAllByCampaignGroupId(campaignGroupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Campaign> create(@RequestBody List<Campaign> campaigns, @RequestParam Long campaignGroupId) {
        return campaignService.create(campaigns, campaignGroupId);
    }
}
