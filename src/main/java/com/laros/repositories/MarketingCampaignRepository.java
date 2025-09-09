package com.laros.repositories;

import com.laros.models.MarketingCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketingCampaignRepository extends
        JpaRepository<MarketingCampaign, Long>, JpaSpecificationExecutor<MarketingCampaign> {
    Page<MarketingCampaign> findByIsActiveTrue(Pageable page);
}
