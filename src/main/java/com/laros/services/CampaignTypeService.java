package com.laros.services;

import com.laros.dtos.campaignType.CreateCampaignType;
import com.laros.dtos.campaignType.UpdateCampaignType;
import com.laros.models.CampaignType;
import com.laros.repositories.CampaignTypeRepository;
import com.laros.repositories.MarketingCampaignResultRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CampaignTypeService {
    @Autowired
    private CampaignTypeRepository campaignTypeRepository;

    public CampaignType create(
            CreateCampaignType createCampaignType
    ) {
        return campaignTypeRepository.save(
                        new CampaignType(createCampaignType)
        );
    }

    public Page<CampaignType> list(Pageable page, String name) {
        if (name != null){
            return campaignTypeRepository.findByNameContainingIgnoreCase(page, name);
        }
        return campaignTypeRepository.findByIsActiveTrue(page);
    }


    public CampaignType update(
            Long id,
            UpdateCampaignType updateCampaignType
    ) {

        CampaignType campaign = campaignTypeRepository.getReferenceById(id);
        campaign.update(updateCampaignType);

        return campaignTypeRepository.save(campaign);
    }

    public void delete(Long id) {
        CampaignType campaign = campaignTypeRepository.getReferenceById(id);
        campaign.delete();
        campaignTypeRepository.save(campaign);
    }

    public CampaignType getById(Long id) {
        return campaignTypeRepository.findById(id).orElseThrow(EntityExistsException::new);
    }
}
