package com.laros.models;

import com.laros.dtos.marketingCampaign.CreateMarketingCampaign;
import com.laros.dtos.marketingCampaign.UpdateMarketingCampaign;
import com.laros.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marketing_campaigns")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class MarketingCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id") // Definimos o lado dono do relacionamento
    private Project project;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private MarketingCampaignResult result;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Task> taks;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform; //Meta Ads, Google Ads (PickList)

    @ManyToOne
    @JoinColumn(name = "campaign_type")
    private CampaignType campaignType; //Reconhecimento, trafego engajamento, leads, promoção do app, vendas (Picklist)
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @Column(nullable = false)
    private boolean isActive = true;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public MarketingCampaign(
            CreateMarketingCampaign createCampaign,
            User createdBy,
            Project project,
            Platform platform,
            CampaignType campaignType
    ) {
        this.name = createCampaign.name();
        this.createdBy = createdBy;
        this.project = project;
        this.platform = platform;
        this.taks = new ArrayList<>();
        this.campaignType = campaignType;
        this.status = CampaignStatus.PLANING;
        this.startDate = createCampaign.startDate();
        this.endDate = createCampaign.endDate();
    }

    public void delete() {
        this.isActive = false;
    }

    public void update(
            UpdateMarketingCampaign updateMarketingCampaign,
            Project project,
            CampaignType campaignType,
            Platform platform
    ) {
        if (updateMarketingCampaign.name() != null) {
            this.name = updateMarketingCampaign.name();
        }
        if (project != null) {
            this.project = project;
        }
        if (platform != null) {
            this.platform = platform;
        }
        if (campaignType != null) {
            this.campaignType = campaignType;
        }
        if (updateMarketingCampaign.campaignStatus() != null) {
            this.status = updateMarketingCampaign.campaignStatus();
        }
        if (updateMarketingCampaign.startDate() != null) {
            this.startDate = updateMarketingCampaign.startDate();
        }
        if (updateMarketingCampaign.endDate() != null) {
            this.endDate = updateMarketingCampaign.endDate();
        }
    }
}
