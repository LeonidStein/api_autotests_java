package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGamePublishDateCondition(String oldPublishDate) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String newPublishDate = extractClass(response, GamesItemModel.class).getPublishDate();

        assertThat(newPublishDate).as("Проверка обновления поля publish_date")
                                  .isNotNull()
                                  .isNotBlank()
                                  .isNotEqualTo(oldPublishDate);
    }
}
