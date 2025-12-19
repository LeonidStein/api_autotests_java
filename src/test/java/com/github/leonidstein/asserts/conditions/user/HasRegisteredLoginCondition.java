package com.github.leonidstein.asserts.conditions.user;

import com.github.leonidstein.asserts.conditions.Condition;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static org.assertj.core.api.Assertions.assertThat;

public record HasRegisteredLoginCondition(String expectedLogin) implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final String[] loginUsers = extractClass(response, String[].class);
        final boolean isFoundLogin = Arrays.asList(loginUsers).contains(expectedLogin);

        assertThat(loginUsers.length).as("Проверка массива с логинами пользователей")
                                     .isGreaterThanOrEqualTo(100);

        assertThat(isFoundLogin).as("Проверка содержания ожидаемого логина в массиве с логинами")
                                .isTrue();
    }
}
