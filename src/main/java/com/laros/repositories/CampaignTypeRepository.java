package com.laros.repositories;

import com.laros.models.CampaignType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignTypeRepository extends JpaRepository<CampaignType, Long> {
    Page<CampaignType> findByIsActiveTrue(Pageable page);
    Page<CampaignType> findByNameContainingIgnoreCase(Pageable page, String name);
}
