package com.laros.models;

import com.laros.dtos.marketingCampaignResult.CreateMarketingCampaignResult;
import com.laros.dtos.marketingCampaignResult.UpdateMarketingResultCampaign;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marketing_campaigns_result")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class MarketingCampaignResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal investment = BigDecimal.ZERO;
    private BigDecimal costs = BigDecimal.ZERO;
    private BigDecimal revenue = BigDecimal.ZERO;

    // Calculated fields
    private BigDecimal roi = BigDecimal.ZERO;
    private BigDecimal netProfit = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne(mappedBy ="result")
    private MarketingCampaign marketingCampaign;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public MarketingCampaignResult(CreateMarketingCampaignResult createCampaign) {
        this.investment = createCampaign.investment() != null ? createCampaign.investment() : BigDecimal.ZERO;
        this.revenue = createCampaign.revenue() != null ? createCampaign.revenue() : BigDecimal.ZERO;
        this.costs = createCampaign.costs() != null ? createCampaign.costs() : BigDecimal.ZERO;
    }

    public BigDecimal getRoi() {
        return calculateRoi();
    }

    private BigDecimal calculateRoi() {
        if (investment == null || BigDecimal.ZERO.compareTo(investment) == 0) {
            System.out.println("Cai no IF");
            return BigDecimal.ZERO;
        }

        this.setNetProfit(calculateNetProfit(investment, costs));
        System.out.println("profit " + this.netProfit);

        return this.netProfit
                .divide(investment, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateNetProfit(BigDecimal investment, BigDecimal costs) {
        var result = revenue
                .subtract(investment)
                .subtract(costs);
        return result;
    }

    @PrePersist
    @PreUpdate
    public void updateCalculateFields() {
        System.out.println("updateCalculateFields foi chamado");
        this.setRoi(calculateRoi());
        System.out.println("Campos calculados atualizados: ROI=" + this.roi + ", NetProfit=" + this.netProfit);

    }

    public void update(UpdateMarketingResultCampaign updtResult) {
        if (updtResult.investment() != null) {
            investment = updtResult.investment();
        }

        if (updtResult.revenue() != null) {
            revenue = updtResult.revenue();
        }

        if (updtResult.costs() != null) {
            costs = updtResult.costs();
        }

        if (updtResult.isActive() != null) {
            isActive = updtResult.isActive();
        }
    }

    public void delete() {
        this.isActive = false;
    }
}

