package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.MAX_RATING;
import static com.github.leonidstein.data.FakeData.MIN_RATING;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameRatingCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Integer rating = extractListOrSingle(response, GamesItemModel.class).getFirst().getRating();

        assertThat(rating).as("Проверка поля rating")
                          .isNotNull()
                          .isBetween(MIN_RATING, MAX_RATING);
    }
}
