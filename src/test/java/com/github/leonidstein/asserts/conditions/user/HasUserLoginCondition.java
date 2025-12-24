package com.github.leonidstein.asserts.conditions.user;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.info.RegisterDataModel;
import com.github.leonidstein.models.response.info.UserDataModel;
import io.restassured.response.ValidatableResponse;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasUserLoginCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        String login;

        try {
            login = extractClass(response, RegisterDataModel.class).getRegisterData().getLogin();
        } catch (final Exception exception) {
            login = extractClass(response, UserDataModel.class).getLogin();
        }

        assertThat(login).as("Проверка поля login")
                         .isNotNull()
                         .isNotBlank();
    }
}
