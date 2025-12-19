package com.github.leonidstein.tests;

import com.github.leonidstein.models.request.field.GameFieldsModel;
import com.github.leonidstein.models.request.user.create.DlcsItemModel;
import com.github.leonidstein.models.request.user.create.NewUserModel;
import com.github.leonidstein.resolvers.UserResolver;
import com.github.leonidstein.resolvers.UserVariant;
import com.github.leonidstein.utils.annotations.TestType;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
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

import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentCompany;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentDescription;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentGenre;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentIsFree;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentPrice;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentPublishDate;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentRating;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentRequiredAge;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentSizeDlc;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasDifferentTitle;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasMessage;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatus;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasStatusCode;
import static com.github.leonidstein.asserts.conditions.ConditionManager.hasToken;
import static com.github.leonidstein.builders.GameBuilder.createdUpdatedDlc;
import static com.github.leonidstein.builders.GameFieldBuilder.updateCompanyField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateDescriptionField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGameGenreField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGameIdField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGameIsFreeField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGamePriceField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGamePublishDateField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGameRatingField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateGameRequiredAgeField;
import static com.github.leonidstein.builders.GameFieldBuilder.updateTitleField;
import static com.github.leonidstein.constants.info.Message.CANNOT_EDIT_ID_FIELD;
import static com.github.leonidstein.constants.info.Message.DLC_SUCCESSFULLY_CHANGED;
import static com.github.leonidstein.constants.info.Message.EMPTY_BODY_WITH_LIST_OF_DLC_TO_MODIFY;
import static com.github.leonidstein.constants.info.Message.GAME_WITH_THIS_ID_NOT_EXIST;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_COMPANY;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_DESCRIPTION;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_GENRE;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_IS_FREE;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_PRICE;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_PUBLISH_DATE;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_RATING;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_REQUIRED_AGE;
import static com.github.leonidstein.constants.info.Message.NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_TITLE;
import static com.github.leonidstein.constants.info.Status.FAIL;
import static com.github.leonidstein.constants.info.Status.SUCCESS;
import static com.github.leonidstein.data.FakeDataManager.getRandomIntegerFrom0ToMaxIntValue;
import static com.github.leonidstein.resolvers.UserType.NEW_USER;
import static com.github.leonidstein.resolvers.UserVariantType.WITH_GAME_FOR_UPDATE;
import static com.github.leonidstein.utils.annotations.TestType.Type.NEGATIVE;
import static com.github.leonidstein.utils.annotations.TestType.Type.POSITIVE;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.MINOR;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("API. game-controller-new")
@Feature("API. Обновление списка DLC у игры, обновление полей игры")
@ExtendWith(UserResolver.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public final class UpdateGameTest extends BaseTest {

    private String token;
    private Integer gameId;

    private String oldCompany;
    private String oldDescription;
    private String oldGenre;
    private String oldTitle;
    private String oldPublishDate;
    private double oldPrice;
    private int oldRating;
    private boolean oldIsFree;
    private boolean oldRequiredAge;

    @BeforeEach
    void initUserCredentials(@UserVariant(user = NEW_USER, variant = WITH_GAME_FOR_UPDATE) final NewUserModel user) {

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

        this.oldCompany = user.getGames().getFirst().getCompany();
        this.oldDescription = user.getGames().getFirst().getDescription();
        this.oldGenre = user.getGames().getFirst().getGenre();
        this.oldTitle = user.getGames().getFirst().getTitle();
        this.oldPrice = user.getGames().getFirst().getPrice();
        this.oldRating = user.getGames().getFirst().getRating();
        this.oldIsFree = user.getGames().getFirst().getIsFree();
        this.oldRequiredAge = user.getGames().getFirst().getRequiredAge();
        this.oldPublishDate = user.getGames().getFirst().getPublishDate();
    }

    @DisplayName("Полное обновление списка DLC у игры")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/50")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(POSITIVE)
    @Test
    public void testUpdateDlc() {

        final List<DlcsItemModel> dlc = List.of(createdUpdatedDlc());

        gameService
                .makeRequest()
                    .updateDlcInfo(token, gameId, dlc)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(DLC_SUCCESSFULLY_CHANGED.getMessage()));

        gameService
                .makeRequest()
                    .getGames(token)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentSizeDlc());
    }

    @DisplayName("Обновление списка DLC по несуществующему ID игры")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/93")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @Test
    public void testUpdateDlcWithNonExistingId() {

        final List<DlcsItemModel> dlc = List.of(createdUpdatedDlc());
        final int nonExistingGameId = getRandomIntegerFrom0ToMaxIntValue();

        gameService
                .makeRequest()
                    .updateDlcInfo(token, nonExistingGameId, dlc)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(GAME_WITH_THIS_ID_NOT_EXIST.getMessage()));
    }

    @DisplayName("Обновление списка DLC без его указания")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/94")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testUpdateDlcWithoutDlc() {

        gameService
                .makeRequest()
                    .updateDlcInfoWithoutDlc(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(EMPTY_BODY_WITH_LIST_OF_DLC_TO_MODIFY.getMessage()));
    }

    @DisplayName("Обновление списка DLC без токена авторизации")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/95")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(NORMAL)
    @TestType(NEGATIVE)
    @Test
    public void testUpdateDlcWithoutToken() {

        final List<DlcsItemModel> dlc = List.of(createdUpdatedDlc());

        gameService
                .makeRequest()
                    .updateDlcInfoWithoutToken(gameId, dlc)
                .checkResponse()
                    .should(hasStatusCode(SC_UNAUTHORIZED));
    }

    @DisplayName("Обновление поля company")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/98")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateCompanyField() {

        final GameFieldsModel updateCompanyField = updateCompanyField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateCompanyField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_COMPANY.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentCompany(oldCompany));
    }

    @DisplayName("Обновление поля description")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/99")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateDescriptionField() {

        final GameFieldsModel updateDescriptionField = updateDescriptionField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateDescriptionField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_DESCRIPTION.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentDescription(oldDescription));
    }

    @DisplayName("Обновление поля genre")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/100")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateGenreField() {

        final GameFieldsModel updateGenreField = updateGameGenreField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateGenreField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_GENRE.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentGenre(oldGenre));
    }

    @DisplayName("Обновление поля title")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/101")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateTitleField() {

        final GameFieldsModel updateTitleField = updateTitleField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateTitleField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_TITLE.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentTitle(oldTitle));
    }

    @DisplayName("Обновление неизменяемого поля gameId")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/102")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(CRITICAL)
    @TestType(NEGATIVE)
    @Test
    public void testUpdateGameIdField() {

        final GameFieldsModel updateGameIdField = updateGameIdField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateGameIdField)
                .checkResponse()
                    .should(hasStatusCode(SC_BAD_REQUEST))
                    .should(hasStatus(FAIL.getStatus()))
                    .should(hasMessage(CANNOT_EDIT_ID_FIELD.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK));
    }

    @DisplayName("Обновление поля price")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/135")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdatePriceField() {

        final GameFieldsModel updatePriceField = updateGamePriceField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updatePriceField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_PRICE.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentPrice(oldPrice));
    }

    @DisplayName("Обновление поля rating")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/136")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateRatingField() {

        final GameFieldsModel updateRatingField = updateGameRatingField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateRatingField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_RATING.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentRating(oldRating));
    }

    @DisplayName("Обновление поля isFree")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/137")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateIsFreeField() {

        final GameFieldsModel updateIsFreeField = updateGameIsFreeField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateIsFreeField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_IS_FREE.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentIsFree(oldIsFree));
    }

    @DisplayName("Обновление поля requiredAge")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/138")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdateRequiredAgeField() {

        final GameFieldsModel updateRequiredAgeField = updateGameRequiredAgeField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updateRequiredAgeField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_REQUIRED_AGE.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentRequiredAge(oldRequiredAge));
    }

    @Issue("58980340")
    @DisplayName("Обновление поля publish_date")
    @Owner("Бурштейн Л.О.")
    @TmsLink("tests/139")
    @Tags({@Tag("api"), @Tag("api-regress")})
    @Severity(MINOR)
    @TestType(POSITIVE)
    @Test
    public void testUpdatePublishDateField() {

        final GameFieldsModel updatePublishDateField = updateGamePublishDateField();

        gameService
                .makeRequest()
                    .updateGameField(token, gameId, updatePublishDateField)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasStatus(SUCCESS.getStatus()))
                    .should(hasMessage(NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_PUBLISH_DATE.getMessage()));

        gameService
                .makeRequest()
                    .getGameByGameId(token, gameId)
                .checkResponse()
                    .should(hasStatusCode(SC_OK))
                    .should(hasDifferentPublishDate(oldPublishDate));
    }
}