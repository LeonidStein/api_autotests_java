package com.github.leonidstein.resolvers;

import com.github.leonidstein.models.request.user.create.NewUserModel;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import static com.github.leonidstein.builders.UserBuilder.createUserWithGame;
import static com.github.leonidstein.builders.UserBuilder.createUserWithGameForUpdate;
import static com.github.leonidstein.builders.UserBuilder.createUserWithoutGame;
import static com.github.leonidstein.builders.UserBuilder.getRegisteredUser;
import static com.github.leonidstein.builders.UserBuilder.getRegisteredUserWithGame;
import static com.github.leonidstein.builders.UserBuilder.getRegisteredUserWithTwentyGames;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserType.TEST_USER;

public final class UserResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        return parameterContext.isAnnotated(UserVariant.class)
                &&
                parameterContext.getParameter().getType() == NewUserModel.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        final UserVariant annotation = parameterContext
                .findAnnotation(UserVariant.class)
                .orElseThrow(() ->
                        new ParameterResolutionException("Аннотация @UserVariant не найдена"));

        if (annotation.user().equals(NEW_USER)) {
            return switch (annotation.variant()) {

                case WITHOUT_GAME -> createUserWithoutGame();

                case WITH_GAME -> createUserWithGame();

                case WITH_GAME_FOR_UPDATE -> createUserWithGameForUpdate();

                default -> throw new IllegalStateException("Неизвестное значение: " + annotation.variant());
            };
        } else if (annotation.user().equals(TEST_USER)) {
            return switch (annotation.variant()) {

                case WITHOUT_GAME -> getRegisteredUser();

                case WITH_GAME -> getRegisteredUserWithGame();

                case WITH_TWENTY_GAMES -> getRegisteredUserWithTwentyGames();

                default -> throw new IllegalStateException("Неизвестное значение: " + annotation.variant());
            };
        } else {
            throw new IllegalStateException("Неизвестный пользователь: " + annotation.user());
        }
    }

}
