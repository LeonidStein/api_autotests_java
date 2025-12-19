package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.MAX_PRICE;
import static com.github.leonidstein.data.FakeData.MIN_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGamePriceCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final Double price = extractListOrSingle(response, GamesItemModel.class).getFirst().getPrice();

        assertThat(price).as("Проверка поля price")
                         .isNotNull()
                         .isBetween(MIN_PRICE, MAX_PRICE);
    }
}
