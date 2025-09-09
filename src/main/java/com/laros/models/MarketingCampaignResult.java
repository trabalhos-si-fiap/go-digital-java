package com.laros.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private BigDecimal financialResult = BigDecimal.ZERO;
    private BigDecimal roi = BigDecimal.ZERO;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BigDecimal getRoi(){
        return calculateRoi();
    }

    private BigDecimal calculateRoi(){
        if (investment == null || BigDecimal.ZERO.compareTo(investment) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal netProfit = financialResult
                .subtract(investment)
                .subtract(costs);

        return netProfit
                .divide(investment, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @PrePersist
    @PreUpdate
    public void updateRoi() {
        this.roi = calculateRoi();
    }
}

