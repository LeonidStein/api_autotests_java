package com.github.leonidstein.asserts.conditions.train;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.train.VariousKeyModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasVariousKeyCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final VariousKeyModel variousKey = extractClass(response, VariousKeyModel.class);

        assertThat(variousKey.getJsonMember1()).as("Проверка поля 1")
                                               .isNotNull()
                                               .isNotBlank();

        assertThat(variousKey.getJsonMember2()).as("Проверка поля _2")
                                               .isNotNull()
                                               .isNotBlank();

        assertThat(variousKey.getNumbersPow().getNums().getJsonMember1()).as("Проверка поля 1 в объекте nums")
                                                                         .isNotNull()
                                                                         .isNotBlank();

        assertThat(variousKey.getNumbersPow().getNums().getJsonMember2()).as("Проверка поля 2 в объекте nums")
                                                                         .isNotNull()
                                                                         .isNotBlank();

        assertThat(variousKey.getNumbersPow().getNums().getJsonMember3()).as("Проверка поля 3 в объекте nums")
                                                                         .isNotNull()
                                                                         .isNotBlank();

        assertThat(variousKey.getNumbersPow().getNums().getJsonMember4()).as("Проверка поля 4 в объекте nums")
                                                                         .isNotNull()
                                                                         .isNotBlank();

        assertThat(variousKey.getBoolTrue()).as("Проверка поля true")
                                            .isNotNull();

        assertThat(variousKey.getSomethingInRussian()).as("Проверка поля что то на русском")
                                                      .isNotNull()
                                                      .isNotBlank();

        assertThat(variousKey.getBmwUsers()).as("Проверка поля bmw:users")
                                            .isNotNull()
                                            .isNotBlank();

        assertThat(variousKey.getSingleQuotes()).as("Проверка поля 'single_quotes'")
                                                .isNotNull()
                                                .isNotBlank();
    }
}
