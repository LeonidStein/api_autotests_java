package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameTitleCondition(String oldTitle) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String newTitle = extractClass(response, GamesItemModel.class).getTitle();

        assertThat(newTitle).as("Проверка обновления поля title")
                            .isNotNull()
                            .isNotBlank()
                            .isNotEqualTo(oldTitle);

    }
}
