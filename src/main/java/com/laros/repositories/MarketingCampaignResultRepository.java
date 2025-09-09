package com.laros.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.laros.models.CampaignType;
import com.laros.models.MarketingCampaignResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketingCampaignResultRepository extends JpaRepository<MarketingCampaignResult, Long> {
    Page<MarketingCampaignResult> findAllByIsActiveTrue(Pageable page);
}
