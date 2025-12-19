package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.regex.Pattern;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGamePublishDateCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String publishDate = extractListOrSingle(response, GamesItemModel.class).getFirst().getPublishDate();

        final Pattern patternDate = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");

        assertThat(publishDate).as("Проверка поля publishDate")
                               .isNotNull()
                               .isNotBlank()
                               .matches(patternDate.pattern());
    }
}
