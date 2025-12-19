package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGamePriceCondition(double oldPrice) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Double newPrice = extractClass(response, GamesItemModel.class).getPrice();

        assertThat(newPrice).as("Проверка обновления поля price")
                            .isNotNull()
                            .isNotEqualTo(oldPrice);
    }
}
