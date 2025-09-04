package com.laroz.repositories;

import com.laroz.models.CampaignType;
import com.laroz.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampaignTypeRepository extends JpaRepository<CampaignType, Long> {
    Page<CampaignType> findByIsActiveTrue(Pageable page);
}
