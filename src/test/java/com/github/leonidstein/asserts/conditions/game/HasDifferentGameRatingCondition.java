package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameRatingCondition(int oldRating) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Integer newRating = extractClass(response, GamesItemModel.class).getRating();

        assertThat(newRating).as("Проверка обновление поля rating")
                             .isNotNull()
                             .isNotEqualTo(oldRating);
    }
}
