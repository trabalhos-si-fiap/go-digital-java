package com.laros.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MarketingCampaignResultTest {

    @Test
    void shouldReturnRoiCorrectlyWhenCallingGetRoi() {
        // Arrange
        var mktResult = new MarketingCampaignResult();

        var costs = BigDecimal.valueOf(2_000);
        var investment = BigDecimal.valueOf(10_000);
        var result = BigDecimal.valueOf(20_000);

        mktResult.setCosts(costs);
        mktResult.setInvestment(investment);
        mktResult.setRevenue(result);

        // ROI:: ((20_000 - 10_000 - 2_000) / 10_000) * 100 = 80%
        var expectedRoi = BigDecimal.valueOf(80.00).setScale(2, RoundingMode.HALF_UP);

        // Act
        var actualRoi = mktResult.getRoi().setScale(2, RoundingMode.HALF_UP);

        // Assert
        assertEquals(expectedRoi, actualRoi, "ROI should be correctly calculated");
    }
}