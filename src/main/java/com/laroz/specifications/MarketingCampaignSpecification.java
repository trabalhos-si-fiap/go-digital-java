package com.laroz.specifications;

import com.laroz.enums.CampaignStatus;
import com.laroz.models.MarketingCampaign;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketingCampaignSpecification {

    // Filtro por nome da campanha
    public static Specification<MarketingCampaign> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Filtro pelo ID do projeto associado
    public static Specification<MarketingCampaign> hasProject(Long projectId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("project").get("id"), projectId);
    }

    // Filtro pelo ID da plataforma (Google Ads, Meta Ads etc.)
    public static Specification<MarketingCampaign> hasPlatform(Long platformId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("platform").get("id"), platformId);
    }

    // Filtro por investimento (maior ou igual)
    public static Specification<MarketingCampaign> hasInvestment(BigDecimal investment) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("investment"), investment);
    }

    // Filtro por ID do tipo de campanha
    public static Specification<MarketingCampaign> hasCampaignType(Long campaignTypeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("campaignType").get("id"), campaignTypeId);
    }

    // Filtro pelo status da campanha
    public static Specification<MarketingCampaign> hasStatus(CampaignStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    // Filtro pela data de início (igual ou maior que uma data específica)
    public static Specification<MarketingCampaign> hasStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    // Filtro pela data de fim (igual ou menor que uma data específica)
    public static Specification<MarketingCampaign> hasEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    // Filtro por campanhas ativas
    public static Specification<MarketingCampaign> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive"));
    }
}
