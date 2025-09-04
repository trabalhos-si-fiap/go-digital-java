package com.laroz.models;

import com.laroz.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private BigDecimal investment;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Task> taks;

    @OneToOne
    @JoinColumn(name = "platform_id")
    private Platform platform; //Meta Ads, Google Ads (PickList)

    @OneToOne
    @JoinColumn(name="campaign_type")
    private CampaignType type; //Reconhecimento, trafego engajamento, leads, promoção do app, vendas (Picklist)
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @Column(nullable = false)
    private boolean isActive = true;

    private LocalDate startDate;
    private LocalDate endDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
