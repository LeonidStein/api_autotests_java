package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameRequiredAgeCondition(boolean oldRequiredAge) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Boolean newRequiredAge = extractClass(response, GamesItemModel.class).getRequiredAge();

        assertThat(newRequiredAge).as("Проверка обновления поля requiredAge")
                                  .isNotNull()
                                  .isNotEqualTo(oldRequiredAge);

    }
}
