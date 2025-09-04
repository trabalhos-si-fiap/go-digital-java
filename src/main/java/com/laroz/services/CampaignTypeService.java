package com.laroz.services;

import com.laroz.dtos.campaignType.CreateCampaignType;
import com.laroz.dtos.campaignType.UpdateCampaignType;
import com.laroz.models.CampaignType;
import com.laroz.repositories.CampaignTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignTypeService {
    @Autowired
    private CampaignTypeRepository campaignTypeRepository;

    public CampaignType create(
            CreateCampaignType createCampaignType,
            Authentication authentication
    ) {
        return campaignTypeRepository.save(
                        new CampaignType(createCampaignType)
        );
    }

    public List<CampaignType> list(Pageable page) {
        return campaignTypeRepository.findByIsActiveTrue(page).toList();
    }


    public CampaignType update(
            UpdateCampaignType updateCampaignType,
            Authentication authentication
    ) {

        CampaignType campaign = campaignTypeRepository.getReferenceById(updateCampaignType.id());
        campaign.update(updateCampaignType);

        return campaignTypeRepository.save(campaign);
    }

    public void delete(Long id) {
        CampaignType campaign = campaignTypeRepository.getReferenceById(id);
        campaign.delete();
        campaignTypeRepository.save(campaign);
    }
}
