package com.laros.repositories;

import com.laros.models.CampaignType;
import com.laros.models.MarketingCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CampaignTypeRepository extends JpaRepository<CampaignType, Long> {
    Page<CampaignType> findByNameContainingIgnoreCase(Pageable page, String name);

    Page<CampaignType> findByIsActiveTrue(Pageable page);
}
