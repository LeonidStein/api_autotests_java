package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.user.create.DlcsItemModel;
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

import java.util.List;

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasNoDlcs;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasNoGames;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.constants.info.Message.GAME_DLC_SUCCESSFULLY_DELETED;
import static com.github.leonidstein.constants.info.Message.GAME_SUCCESSFULLY_DELETED;
import static com.github.leonidstein.constants.info.Message.GAME_WITH_THIS_ID_NOT_EXIST;
import static com.github.leonidstein.constants.info.Message.LIST_WITH_DLC_TO_DELETE_CANT_BE_EMPTY_OR_NULL;
import static com.github.leonidstein.constants.info.Status.FAIL;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.data.FakeDataManager.getRandomIntFromTo;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITH_GAME;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. game-controller-new")
@Feature("API. Удаление игры, удаление DLC")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class DeleteGameTest extends BaseTest {

    private String token;
    private Integer gameId;
    private List<DlcsItemModel> dlcsItems;

    @BeforeEach
    void initUserCredentials(@UserVariant(user = NEW_USER, variant = WITH_GAME) final NewUserModel user) {

        this.token = userService.makeRequest()
                                    .registrationWithAuthorization(user)
                                .checkResponse()
                                    .should(hasStatusCode(SC_OK))
                                    .should(hasToken())
                                .extractToken();

        this.gameId = gameService.makeRequest()
                                    .getGames(token)
                                 .checkResponse()
                                    .should(hasStatusCode(SC_OK))
                                 .extractGameId();

        this.dlcsItems = user.getGames().getFirst().getDlcs();
    }

    @DisplayName("Удаление игры по её ID у пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/45")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testDeleteGameById() {

        gameService
                .makeRequest()
                    .deleteGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(GAME_SUCCESSFULLY_DELETED.getMessage()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasNoGames());
    }

    @DisplayName("Повторное удаление игры по её ID у пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/46")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testReDeletingGameById() {

        gameService
                .makeRequest()
                    .deleteGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(GAME_SUCCESSFULLY_DELETED.getMessage()));

        gameService
                .makeRequest()
                    .deleteGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(GAME_WITH_THIS_ID_NOT_EXIST.getMessage()));
    }

    @DisplayName("Удаление игры по её несуществующему ID у пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/52")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testDeleteGameByNonExistingId() {

        int nonExistingId = getRandomIntFromTo(1, Integer.MAX_VALUE);

        gameService
                .makeRequest()
                    .deleteGameByGameId(token, nonExistingId)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(GAME_WITH_THIS_ID_NOT_EXIST.getMessage()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasGames());
    }

    @DisplayName("Удаление игры по её ID у пользователя без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/54")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testDeleteGameByIdWithoutToken() {

        gameService
                .makeRequest()
                    .deleteGameByGameIdWithoutToken(gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Удаление DLC по ID игры у пользователя с указанием конкретного dlc")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/47")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testDeleteDlcById() {

        gameService
                .makeRequest()
                    .deleteDlcByGameIdWithDlc(token, gameId, dlcsItems)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(GAME_DLC_SUCCESSFULLY_DELETED.getMessage()));

        gameService
                .makeRequest()
                    .getGames(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasNoDlcs());
    }

    @DisplayName("Повторное удаление DLC по ID игры у пользователя с указанием конкретного dlc")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/48")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testReDeleteDlcById() {

        gameService
                .makeRequest()
                    .deleteDlcByGameIdWithDlc(token, gameId, dlcsItems)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(GAME_DLC_SUCCESSFULLY_DELETED.getMessage()));

        gameService
                .makeRequest()
                    .deleteDlcByGameIdWithDlc(token, gameId, dlcsItems)
                .checkResponse()
                    .should(hasStatusCode(SC_OK));
    }

    @DisplayName("Удаление DLC по ID игры у пользователя без указания конкретного dlc")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/49")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testDeleteDlcByIdWithoutSpecificDlc() {

        gameService
                .makeRequest()
                    .deleteDlcByGameIdWithoutDlc(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(LIST_WITH_DLC_TO_DELETE_CANT_BE_EMPTY_OR_NULL.getMessage()));
    }

    @DisplayName("Удаление DLC по её несуществующему ID у пользователя")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/53")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testDeleteDlcByNonExistingId() {

        int nonExistingId = getRandomIntFromTo(1, Integer.MAX_VALUE);

        gameService
                .makeRequest()
                    .deleteDlcByGameIdWithDlc(token, nonExistingId, dlcsItems)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(GAME_WITH_THIS_ID_NOT_EXIST.getMessage()));

        userService
                .makeRequest()
                    .getInfoAboutUser(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasGames());
    }

    @DisplayName("Удаление DLC по её ID у пользователя без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/55")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testDeleteDlcByIdWithoutToken() {

        gameService
                .makeRequest()
                    .deleteDlcByGameIdWithoutToken(gameId, dlcsItems)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }
}
