package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasDifferentGameCompanyCondition(String oldCompany) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String newCompany = extractClass(response, GamesItemModel.class).getCompany();

        assertThat(newCompany).as("Проверка обновления поля company")
                              .isNotNull()
                              .isNotBlank()
                              .isNotEqualTo(oldCompany);
    }
}
