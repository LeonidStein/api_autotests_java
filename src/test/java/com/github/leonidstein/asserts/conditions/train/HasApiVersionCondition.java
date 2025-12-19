package com.github.leonidstein.asserts.conditions.train;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.train.ApiVersionModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasApiVersionCondition(String expectedApiVersion) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String actualVersionApi = extractClass(response, ApiVersionModel.class).getApiVersion();

        assertThat(actualVersionApi).as("Проверка версии API")
                                    .isNotNull()
                                    .isNotBlank()
                                    .isEqualTo(expectedApiVersion);
    }
}
