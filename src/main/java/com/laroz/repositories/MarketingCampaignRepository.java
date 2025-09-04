package com.laroz.repositories;

import com.laroz.models.Comments;
import com.laroz.models.MarketingCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketingCampaignRepository extends JpaRepository<MarketingCampaign, Long> {
}
