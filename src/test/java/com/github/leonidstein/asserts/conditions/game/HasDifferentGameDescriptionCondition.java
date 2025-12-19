package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameDescriptionCondition(String oldDescription) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String newDescription = extractClass(response, GamesItemModel.class).getDescription();

        assertThat(newDescription).as("Проверка обновления поля description")
                                  .isNotNull()
                                  .isNotBlank()
                                  .isNotEqualTo(oldDescription);
    }
}
