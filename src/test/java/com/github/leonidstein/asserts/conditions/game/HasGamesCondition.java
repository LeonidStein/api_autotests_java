package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.info.RegisterDataModel;
import com.github.leonidstein.models.response.info.UserDataModel;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGamesCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        List<Object> games;

        try {
            games = extractClass(response, RegisterDataModel.class).getRegisterData().getGames();
        } catch (final Exception exception) {
            games = extractClass(response, UserDataModel.class).getGames();
        }

        assertThat(games.size()).as("Проверка массивы games у пользователя")
                                .isGreaterThanOrEqualTo(1);
    }
}
