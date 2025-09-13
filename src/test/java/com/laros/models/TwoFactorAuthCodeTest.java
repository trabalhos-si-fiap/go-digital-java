package com.laros.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TwoFactorAuthCodeTest {

    private User mockUser;
    @BeforeEach
    void setup() {
        mockUser = new User();
        mockUser.setId(1L);
    }


    @Test
    void shouldGenerateCodeWhenInstanceCreated() {
        TwoFactorAuthCode authCode = new TwoFactorAuthCode(mockUser);

        assertThat(authCode.getCodeFormatted())
                .isNotNull()
                .matches("\\d{3}-\\d{3}"); // Verifica o formato correto
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        assertThatThrownBy(() -> new TwoFactorAuthCode(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User cannot be null");
    }

    @Test
    void shouldGenerateDifferentCodesForDifferentInstances() {
        TwoFactorAuthCode firstCode = new TwoFactorAuthCode(mockUser);
        TwoFactorAuthCode secondCode = new TwoFactorAuthCode(mockUser);

        assertThat(firstCode.getCodeFormatted()).isNotEqualTo(secondCode.getCodeFormatted());
    }

    @Test
    void shouldValidateCodeCorrectly() {
        TwoFactorAuthCode authCode = new TwoFactorAuthCode(mockUser);

        assertThat(
                authCode.validateCode(
                        authCode.getCodeFormatted()
                )
        ).isTrue();
        assertThat(authCode.validateCode("000000")).isFalse();
    }

    @Test
    void shouldCleanInputCode() {
        TwoFactorAuthCode authCode = new TwoFactorAuthCode(mockUser);
        String formattedCode = authCode.getCodeFormatted();
        String validUserInput = formattedCode.replace("-", " ");

        assertThat(authCode.validateCode(validUserInput)).isTrue(); // Testa espaços como input
    }

    @Test
    void shouldExpireAfterGivenExpirationTime() {
        Clock mockClock = Mockito.mock(Clock.class);

        Mockito.when(mockClock.instant()).thenReturn(Instant.parse("2025-01-01T12:00:00Z"));
        Mockito.when(mockClock.getZone()).thenReturn(ZoneId.of("UTC"));

        TwoFactorAuthCode authCode = new TwoFactorAuthCode(mockUser, mockClock);

        assertThat(authCode.isValid()).isTrue();

        Mockito.when(mockClock.instant()).thenReturn(Instant.parse("2025-01-01T12:05:01Z"));

        assertThat(authCode.isValid()).isFalse();
    }

    @Test
    void mustHaveTheUserInstancePassedInTheConstructor() {
        TwoFactorAuthCode authCode = new TwoFactorAuthCode(mockUser);
        assertThat(authCode.getUser()).isSameAs(mockUser);
    }

    @Test
    void shouldBeValidBeforeExpiration() {
        TwoFactorAuthCode authCode = new TwoFactorAuthCode(mockUser);

        assertThat(authCode.isValid()).isTrue();
    }
}