package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.COMPANY_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameCompanyCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String company = extractListOrSingle(response, GamesItemModel.class).getFirst().getCompany();

        assertThat(company).as("Проверка поля company")
                           .isNotNull()
                           .isNotBlank();

        assertThat(COMPANY_ARRAY)
                .as("Проверка поля company: значение должно быть одним из ожидаемых: %s",
                        Arrays.toString(COMPANY_ARRAY))
                .contains(company);
    }
}
