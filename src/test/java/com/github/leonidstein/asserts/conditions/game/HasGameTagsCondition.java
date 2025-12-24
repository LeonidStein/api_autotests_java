package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;
import java.util.List;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.TAG_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameTagsCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final List<String> tags = extractListOrSingle(response, GamesItemModel.class).getFirst().getTags();

        assertThat(tags).as("Проверка массива tags")
                        .isNotEmpty();

        assertThat(tags).as("Проверка массива tags: " +
                                "значение должно быть одним из ожидаемых: %s", Arrays.toString(TAG_ARRAY))
                        .isSubsetOf(TAG_ARRAY);
    }
}
