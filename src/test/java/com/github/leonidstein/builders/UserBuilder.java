package com.github.leonidstein.builders;

import com.github.leonidstein.models.request.user.create.GamesItemModel;
import com.github.leonidstein.models.request.user.create.NewUserModel;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.github.leonidstein.config.ConfigManager.config;
import static com.github.leonidstein.data.FakeDataManager.getLogin;
import static com.github.leonidstein.data.FakeDataManager.getPassword;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserBuilder {

    public static NewUserModel buildUser(final String login, final String password) {

        return NewUserModel.builder()
                               .pass(password)
                               .login(login)
                           .build();
    }

    public static NewUserModel buildUser(final String login, final String password, final List<GamesItemModel> games) {

        return NewUserModel.builder()
                               .games(games)
                               .pass(password)
                               .login(login)
                           .build();
    }

    @Step("Тест данные. Создание пользователя с игрой")
    public static NewUserModel createUserWithGame() {

        return buildUser(getLogin(), getPassword(), List.of(GameBuilder.createdGame()));
    }

    @Step("Тест данные. Создание пользователя с игрой для обновления полей")
    public static NewUserModel createUserWithGameForUpdate() {

        return buildUser(getLogin(), getPassword(), List.of(GameBuilder.createdGameForUpdate()));
    }

    @Step("Тест данные. Создание пользователя без игр")
    public static NewUserModel createUserWithoutGame() {

        return buildUser(getLogin(), getPassword());
    }

    @Step("Тест данные. Тестовый пользователь с игрой")
    public static NewUserModel getRegisteredUserWithGame() {

        return buildUser(config().loginTestUserHasGame(), config().passwordTestUserHasGame());
    }

    @Step("Тест данные. Тестовый пользователь без игр")
    public static NewUserModel getRegisteredUser() {

        return buildUser(config().loginTestUserHasNotGames(), config().passwordTestUserHasNotGames());
    }

    @Step("Тест данные. Тестовый пользователь с 20 играми")
    public static NewUserModel getRegisteredUserWithTwentyGames() {

        return buildUser(config().loginTestUserHasTwentyGames(), config().passwordTestUserHasTwentyGames());
    }
}
