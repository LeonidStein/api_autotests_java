package com.github.leonidstein.asserts.conditions.common;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.info.InfoModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasMessageCondition(String expectedMessage) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String actualMessage = extractClass(response, InfoModel.class).getInfo().getMessage();

        assertThat(actualMessage).as("Проверка информационного сообщения")
                                 .isNotNull()
                                 .isNotBlank()
                                 .isEqualTo(expectedMessage);
    }
}
