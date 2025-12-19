package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameIsFreeCondition(boolean oldIsFree) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Boolean newIsFree = extractClass(response, GamesItemModel.class).getIsFree();

        assertThat(newIsFree).as("Проверка обновления поля isFree")
                             .isNotNull()
                             .isNotEqualTo(oldIsFree);
    }
}
