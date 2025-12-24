package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameGenreCondition(String oldGenre) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String newGenre = extractClass(response, GamesItemModel.class).getGenre();

        assertThat(newGenre).as("Проверка обновления поля genre")
                            .isNotNull()
                            .isNotBlank()
                            .isNotEqualTo(oldGenre);
    }
}
