package com.optily.challenge.service.impl;

import com.optily.challenge.exception.BadRequestException;
import com.optily.challenge.exception.ElementNotFoundException;
import com.optily.challenge.model.CampaignGroup;
import com.optily.challenge.repository.CampaginGroupRepository;
import com.optily.challenge.service.CampaignGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignGroupServiceImpl implements CampaignGroupService {
    private final CampaginGroupRepository campaginGroupRepository;

    @Autowired
    CampaignGroupServiceImpl(CampaginGroupRepository campaginGroupRepository) {
        this.campaginGroupRepository = campaginGroupRepository;
    }

    @Override
    public List<CampaignGroup> getAll() {
        return campaginGroupRepository.findAll();
    }

    @Override
    public CampaignGroup getById(Long id) {
        return campaginGroupRepository
                .findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find campaign with ID provided"));
    }

    @Override
    public CampaignGroup create(CampaignGroup campaignGroup) {
        if (campaignGroup.getId() != null) {
            throw new BadRequestException("The ID must not be provided when creating a new campaignGroup");
        }
        return campaginGroupRepository.save(campaignGroup);
    }
}

