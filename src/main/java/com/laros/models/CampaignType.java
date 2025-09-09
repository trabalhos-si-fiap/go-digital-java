package com.laros.models;

import com.laros.dtos.campaignType.CreateCampaignType;
import com.laros.dtos.campaignType.UpdateCampaignType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_type")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class CampaignType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false)
    private boolean isActive = true;

    public CampaignType(CreateCampaignType createCampaignType) {
        this.name = createCampaignType.name();
    }

    public void delete() {
        this.isActive = false;
    }

    public void update(UpdateCampaignType updateCampaignType) {
        if (updateCampaignType.name() != null) {
            this.name = updateCampaignType.name();
        }
    }
}
