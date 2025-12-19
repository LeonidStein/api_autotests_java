package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.GENRE_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameGenreCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String genre = extractListOrSingle(response, GamesItemModel.class).getFirst().getGenre();

        assertThat(genre).as("Проверка поля genre")
                         .isNotNull()
                         .isNotBlank();

        assertThat(GENRE_ARRAY).as("Проверка поля genre: " +
                                       "значение должно быть одним из ожидаемых: %s", Arrays.toString(GENRE_ARRAY))
                               .contains(genre);
    }
}
