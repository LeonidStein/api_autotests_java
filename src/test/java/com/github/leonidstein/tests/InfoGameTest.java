package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.resolvers.UserResolver;
import com.github.leonidstein.resolvers.UserVariant;
import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasCompany;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDescription;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDlcs;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasGameId;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasGenre;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasIsFree;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasPrice;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasPublishDate;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasRating;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasRequiredAge;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasRequirements;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasSimilarDlc;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasTags;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasTitle;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.constants.info.Message.GAME_WITH_THIS_ID_NOT_EXIST;
import static com.github.leonidstein.constants.info.Status.FAIL;
import static com.github.leonidstein.data.FakeDataManager.getRandomIntFromTo;
import static com.github.leonidstein.resolvers.UserType.TEST_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITH_GAME;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. game-controller-new")
@Feature("API. Получение информации об игре или играх")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class InfoGameTest extends BaseTest {

    private String token;
    private Integer gameId;

    @BeforeEach
    void initUserCredentials(@UserVariant(user = TEST_USER, variant = WITH_GAME) final NewUserModel user) {

        this.token = userService.makeRequest()
                                    .authorization(user)
                                .checkResponse()
                                    .should(hasStatusCode(SC_OK))
                                    .should(hasToken())
                                .extractToken();

        this.gameId = gameService.makeRequest()
                                    .getGames(token)
                                 .checkResponse()
                                    .should(hasStatusCode(SC_OK))
                                 .extractGameId();
    }

    @DisplayName("Получение информации об играх пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/41")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testGetInfoGames() {

        gameService
                .makeRequest()
                    .getGames(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasGameId())
                    .should(hasTitle())
                    .should(hasGenre())
                    .should(hasRequiredAge())
                    .should(hasIsFree())
                    .should(hasPrice())
                    .should(hasCompany())
                    .should(hasPublishDate())
                    .should(hasRating())
                    .should(hasDescription())
                    .should(hasTags())
                    .should(hasDlcs())
                    .should(hasSimilarDlc())
                    .should(hasRequirements());
    }

    @DisplayName("Получение информации об игре по её ID")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/43")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testGetInfoGamesById() {

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasGameId())
                    .should(hasTitle())
                    .should(hasGenre())
                    .should(hasRequiredAge())
                    .should(hasIsFree())
                    .should(hasPrice())
                    .should(hasCompany())
                    .should(hasPublishDate())
                    .should(hasRating())
                    .should(hasDescription())
                    .should(hasTags())
                    .should(hasDlcs())
                    .should(hasSimilarDlc())
                    .should(hasRequirements());
    }

    @DisplayName("Получение информации об игре по несуществующему ID")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testGetInfoGameByNonExistingId() {

        int nonExistingGameId = getRandomIntFromTo(1, Integer.MAX_VALUE);

        gameService
                .makeRequest()
                    .getGameByGameId(token, nonExistingGameId)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasMessage(GAME_WITH_THIS_ID_NOT_EXIST.getMessage()))
                    .should(hasStatus(FAIL.getStatus()));
    }

    @DisplayName("Получение информации об играх пользователя без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testGetInfoGamesWithoutToken() {

        gameService
                .makeRequest()
                    .getGamesWithoutToken()
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Получение информации об игре по её ID без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(POSITIVE)
    @Test
    public void testGetInfoGamesByIdWithoutToken() {

        gameService
                .makeRequest()
                    .getGameByGameIdWithoutToken(gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

}
