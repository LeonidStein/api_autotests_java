package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.DESCRIPTION_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameDescriptionCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String gameDescription = extractListOrSingle(response, GamesItemModel.class).getFirst().getDescription();

        assertThat(gameDescription).as("Проверка поля description")
                                   .isNotNull()
                                   .isNotBlank();

        assertThat(DESCRIPTION_ARRAY)
                .as("Проверка поля description: значение должно быть одним из ожидаемых: %s",
                        Arrays.toString(DESCRIPTION_ARRAY))
                .contains(gameDescription);
    }
}
