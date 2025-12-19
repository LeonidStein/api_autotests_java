package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractList;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasTwentyGamesCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Integer actualQuantityGames = extractList(response, GamesItemModel.class).size();
        final int expectedQuantityGames = 20;

        assertThat(actualQuantityGames).as("Проверка количества игр на аккаунте пользователя")
                                       .isNotNull()
                                       .isEqualTo(expectedQuantityGames);
    }
}
