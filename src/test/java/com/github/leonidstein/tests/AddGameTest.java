package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.user.create.GamesItemModel;
import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.resolvers.UserResolver;
import com.github.leonidstein.resolvers.UserType;
import com.github.leonidstein.resolvers.UserVariant;
import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasNoGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasTwentyGames;
import static com.github.leonidstein.builders.GameBuilder.createdFreeGameWithPrice;
import static com.github.leonidstein.builders.GameBuilder.createdGameWithFreeDlcsWithPrice;
import static com.github.leonidstein.builders.GameBuilder.createdGameWithGameId;
import static com.github.leonidstein.builders.GameBuilder.createdPaidGame;
import static com.github.leonidstein.constants.info.Message.FREE_DLC_OR_GAME_CANT_HAVE_PRICE_MORE_THAN_ZERO;
import static com.github.leonidstein.constants.info.Message.GAME_CREATED;
import static com.github.leonidstein.constants.info.Message.LIMIT_OF_GAMES_USER_CAN_HAVE_ONLY_TWENTY_GAMES;
import static com.github.leonidstein.constants.info.Status.FAIL;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserType.TEST_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITHOUT_GAME;
import static com.github.leonidstein.resolvers.UserVariantType.WITH_TWENTY_GAMES;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. game-controller-new")
@Feature("API. Добавление игры пользователю")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class AddGameTest extends BaseTest {

    private String getToken(final NewUserModel user, final UserType userType) {

        if (userType.equals(TEST_USER)) {

            return userService.makeRequest()
                                  .authorization(user)
                              .checkResponse()
                                  .should(hasStatusCode(SC_OK))
                                  .should(hasToken())
                              .extractToken();

        } else if (userType.equals(NEW_USER)) {

            return userService.makeRequest()
                                  .registrationWithAuthorization(user)
                              .checkResponse()
                                  .should(hasStatusCode(SC_OK))
                                  .should(hasToken())
                              .extractToken();
        } else {
            return "Тип пользователя передан неверно";
        }
    }

    private static Stream<Arguments> freeDlcsOrGameWithPrice() {

        return Stream.of(
                Arguments.of(createdGameWithFreeDlcsWithPrice()),
                Arguments.of(createdFreeGameWithPrice())
        );
    }

    @DisplayName("Добавление новой игры пользователю")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/142")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testAddGame(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        final GamesItemModel addedGame = createdPaidGame();
        final String token = getToken(user, NEW_USER);

        gameService
                .makeRequest()
                    .addNewGame(token, addedGame)
                .checkResponse()
                    .should(hasStatusCode(SC_CREATED))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(GAME_CREATED.getMessage()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasGames());
    }

    @Issue("58981151")
    @DisplayName("Добавление новой игры пользователю, который уже имеет макс. предел по играм (20 игр)")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/143")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @Test
    public void testAddGameToUserWhoHasTwentyGames(
            @UserVariant(user = TEST_USER, variant = WITH_TWENTY_GAMES) final NewUserModel user) {

        final GamesItemModel addedGame = createdPaidGame();
        final String token = getToken(user, TEST_USER);

        gameService
                .makeRequest()
                    .addNewGame(token, addedGame)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(LIMIT_OF_GAMES_USER_CAN_HAVE_ONLY_TWENTY_GAMES.getMessage()));

        gameService
                .makeRequest()
                    .getGames(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasTwentyGames());
    }

    @DisplayName("Добавление новой игры или дополнения к игре, которое является бесплатным, но с выставленной ценой")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/144")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @ParameterizedTest
    @MethodSource("freeDlcsOrGameWithPrice")
    public void testAddGameWithFreeDlcsWithPrice(final GamesItemModel addedGame,
            @UserVariant(user = NEW_USER, variant = WITHOUT_GAME) NewUserModel user) {

        final String token = getToken(user, NEW_USER);

        gameService
                .makeRequest()
                    .addNewGame(token, addedGame)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(FREE_DLC_OR_GAME_CANT_HAVE_PRICE_MORE_THAN_ZERO.getMessage()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasNoGames());
    }

    @Issue("58981333")
    @DisplayName("Добавление новой игры пользователю с заполненным полем gameID")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/145")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @Test
    public void testAddGameWithGameId(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        final GamesItemModel addedGame = createdGameWithGameId();
        final String token = getToken(user, NEW_USER);

        gameService
                .makeRequest()
                    .addNewGame(token, addedGame)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasNoGames());
    }

    @DisplayName("Добавление новой игры пользователю без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/146")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testAddGameWithoutToken() {

        final GamesItemModel addedGame = createdPaidGame();

        gameService
                .makeRequest()
                    .addNewGameWithoutToken(addedGame)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Добавление новой игры пользователю без передачи игры")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/147")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testAddGameWithoutGame(@UserVariant(user = NEW_USER, variant = WITHOUT_GAME) final NewUserModel user) {

        final String token = getToken(user, NEW_USER);

        gameService
                .makeRequest()
                    .addNewGameWithoutGame(token)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST));
    }

}
