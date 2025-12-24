package com.github.leonidstein.asserts.conditions.token;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.common.token.TokenModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentTokenCondition(String firstToken) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String secondToken = extractClass(response, TokenModel.class).getToken();

        assertThat(secondToken).as("Проверка JWT токена при повторной авторизации")
                               .isNotNull()
                               .isNotEqualTo(firstToken);
    }
}
