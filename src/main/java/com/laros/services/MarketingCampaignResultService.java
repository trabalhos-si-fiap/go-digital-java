package com.laros.services;

import com.laros.dtos.marketingCampaignResult.CreateMarketingCampaignResult;
import com.laros.dtos.marketingCampaignResult.MarketingCampaignResultResponse;
import com.laros.dtos.marketingCampaignResult.UpdateMarketingResultCampaign;
import com.laros.models.MarketingCampaignResult;
import com.laros.repositories.MarketingCampaignRepository;
import com.laros.repositories.MarketingCampaignResultRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@PreAuthorize("hasRole('ADM')")
@Service
public class MarketingCampaignResultService {
    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    @Autowired
    private MarketingCampaignResultRepository mktResultRepository;

    @Transactional
    public MarketingCampaignResultResponse create(
            CreateMarketingCampaignResult createCampaign
    ) {
        var mktCampaign = marketingCampaignRepository.findById(createCampaign.campaignId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Campanha com ID " + createCampaign.campaignId() + " não encontrada.")
                );

        var mktResult = new MarketingCampaignResult(createCampaign);
//        mktResult.updateCalculateFields();

        mktCampaign.setResult(mktResult);
        mktResult.setMarketingCampaign(mktCampaign);

//        marketingCampaignRepository.save(mktCampaign);
        mktResultRepository.save(mktResult);

        return new MarketingCampaignResultResponse(mktResult);
    }

    public MarketingCampaignResultResponse getById(Long id) {
        var mktResult = mktResultRepository.findById(
                id
        ).orElseThrow(
                () -> new EntityNotFoundException(
                        "Resultado com ID " + id + " não encontrado.")
        );
        return new MarketingCampaignResultResponse(mktResult);
    }

    public Page<MarketingCampaignResultResponse> list(
            Pageable page
    ) {
        return mktResultRepository
                .findAllByIsActiveTrue(page)
                .map(MarketingCampaignResultResponse::new);
    }


    @Transactional
    public MarketingCampaignResultResponse update(
            Long id,
            UpdateMarketingResultCampaign updateMarketingResultCampaign
    ) {
        var mktResult = mktResultRepository.findById(
                id
        ).orElseThrow(
                () -> new EntityNotFoundException(
                        "Resultado com ID " + updateMarketingResultCampaign.id() + " não encontrado.")
        );

        mktResult.update(
                updateMarketingResultCampaign
        );

        mktResultRepository.save(mktResult);
        return new MarketingCampaignResultResponse(mktResult);

    }

    @Transactional
    public void delete(Long id) {
        var mktCampaign = mktResultRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Resultado com ID " + id + " não encontrado.")
                );
        mktCampaign.delete();
        mktResultRepository.save(mktCampaign);
    }
}
