package com.github.leonidstein.asserts.conditions.user;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.info.RegisterDataModel;
import com.github.leonidstein.models.response.info.UserDataModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasUserIdCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        Integer userId;

        try {
            userId = extractClass(response, RegisterDataModel.class).getRegisterData().getId();
        } catch (final Exception exception) {
            userId = extractClass(response, UserDataModel.class).getId();
        }

        assertThat(userId).as("Проверка поля id у пользователя")
                          .isNotNull()
                          .isGreaterThan(0);
    }
}
