package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameIdCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Integer gameId = extractListOrSingle(response, GamesItemModel.class).getFirst().getGameId();

        assertThat(gameId).as("Проверка поля gameId")
                          .isNotNull()
                          .isGreaterThan(0);
    }
}
