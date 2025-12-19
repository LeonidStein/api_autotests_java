package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.TITLE_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameTitleCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String title = extractListOrSingle(response, GamesItemModel.class).getFirst().getTitle();

        assertThat(title).as("Проверка поля title")
                         .isNotNull()
                         .isNotBlank();

        assertThat(TITLE_ARRAY).as("Проверка поля title: значение должно быть одним из ожидаемых: %s",
                                       Arrays.toString(TITLE_ARRAY))
                               .contains(title);
    }
}
