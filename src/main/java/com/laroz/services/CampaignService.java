package com.laroz.services;

import com.laroz.dtos.campaign.CreateMarketingCampaign;
import com.laroz.dtos.campaign.CampaignResponse;
import com.laroz.dtos.campaign.UpdateCampaign;
import com.laroz.models.*;
import com.laroz.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {
    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PlatformRepository platfomRepository;

    @Autowired
    private CampaignTypeRepository campaignTypeRepository;

    @Autowired
    private UserRepository userRepository;

    public CampaignResponse create(
            CreateMarketingCampaign createCampaign,
            Authentication authentication
    ) {

        User createdBy = (User) authentication.getPrincipal();

        Project project = projectRepository.getReferenceById(
                createCampaign.projectId()
        );

        Platform platform = platfomRepository.getReferenceById(
                createCampaign.plataformId()
        );

        CampaignType campaignType = campaignTypeRepository.getReferenceById(
                createCampaign.campaignTypeId()
        );

        var marketingCampaign = new MarketingCampaign(
                createCampaign,
                createdBy,
                project,
                platform,
                campaignType
        );

        return new CampaignResponse(
                marketingCampaignRepository.save(marketingCampaign)
        );
    }

    public List<Platform> list(Pageable page) {
        return campaignRepository.findByIsActiveTrue(page).map(CampaignResponse::new).toList();
    }


    public CampaignResponse update(
            UpdateCampaign updateCampaign,
            Authentication authentication
    ) {

        List<User> members = null;
        List<Client> clients = null;
        User manager = null;

        // Todo: Fazer controle de usuário permitido

        var campaign = campaignRepository.getReferenceById(
                updateCampaign.id()
        );

        // Buscando os dados se foi enviado.

        if (updateCampaign.clientIds() != null) {
            clients = clientRepository.findAllById(
                    updateCampaign.clientIds()
            );
        }

        if (updateCampaign.membersIds() != null) {
            members = userRepository.findAllById(
                    updateCampaign.membersIds()
            );
        }

        if (updateCampaign.managerId() != null) {
            manager = userRepository.getReferenceById(
                    updateCampaign.managerId()
            );
        }

        campaign.update(updateCampaign, clients, members, manager);

        return new CampaignResponse(
                campaignRepository.save(campaign)
        );

    }

    public void delete(Long id) {
        Campaign campaign = campaignRepository.getReferenceById(id);
        campaign.delete();
        campaignRepository.save(campaign);
    }
}
