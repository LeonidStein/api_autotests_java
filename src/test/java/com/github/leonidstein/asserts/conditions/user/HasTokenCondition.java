package com.github.leonidstein.asserts.conditions.user;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.common.token.TokenModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasTokenCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String jwtToken = extractClass(response, TokenModel.class).getToken();

        assertThat(jwtToken).as("Проверка JWT токена")
                            .isNotNull()
                            .isNotBlank();
    }
}
