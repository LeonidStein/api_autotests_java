package com.github.leonidstein.asserts.conditions;

import com.github.leonidstein.asserts.conditions.common.HasMessageCondition;
import com.github.leonidstein.asserts.conditions.common.HasStatusCodeCondition;
import com.github.leonidstein.asserts.conditions.common.HasStatusCondition;
import com.github.leonidstein.asserts.conditions.file.HasDownloadedImageCondition;
import com.github.leonidstein.asserts.conditions.file.HasLastUploadedFileCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameCompanyCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameDescriptionCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameGenreCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameIsFreeCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGamePriceCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGamePublishDateCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameRatingCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameRequiredAgeCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentGameTitleCondition;
import com.github.leonidstein.asserts.conditions.game.HasDifferentSizeGameDlcCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameCompanyCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameDescriptionCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameDlcsCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameGenreCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameIdCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameIsFreeCondition;
import com.github.leonidstein.asserts.conditions.game.HasGamePriceCondition;
import com.github.leonidstein.asserts.conditions.game.HasGamePublishDateCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameRatingCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameRequiredAgeCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameRequirementsCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameSimilarDlcCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameTagsCondition;
import com.github.leonidstein.asserts.conditions.game.HasGameTitleCondition;
import com.github.leonidstein.asserts.conditions.game.HasGamesCondition;
import com.github.leonidstein.asserts.conditions.game.HasTwentyGamesCondition;
import com.github.leonidstein.asserts.conditions.game.NoGameDlcsCondition;
import com.github.leonidstein.asserts.conditions.game.NoGamesCondition;
import com.github.leonidstein.asserts.conditions.status.HasDescriptionStatusCodeCondition;
import com.github.leonidstein.asserts.conditions.token.HasDifferentTokenCondition;
import com.github.leonidstein.asserts.conditions.train.HasApiVersionCondition;
import com.github.leonidstein.asserts.conditions.train.HasCarsCondition;
import com.github.leonidstein.asserts.conditions.train.HasVariousKeyCondition;
import com.github.leonidstein.asserts.conditions.user.HasRegisteredLoginCondition;
import com.github.leonidstein.asserts.conditions.user.HasTokenCondition;
import com.github.leonidstein.asserts.conditions.user.HasUserIdCondition;
import com.github.leonidstein.asserts.conditions.user.HasUserLoginCondition;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConditionManager {

    // < ------------------------ Общие проверки ---------------- >
    @Step("Проверка сообщения")
    public static HasMessageCondition hasMessage(final String expectedMessage) {

        return new HasMessageCondition(expectedMessage);
    }

    @Step("Проверка статуса")
    public static HasStatusCondition hasStatus(final String expectedStatus) {

        return new HasStatusCondition(expectedStatus);
    }

    @Step("Проверка статус кода")
    public static HasStatusCodeCondition hasStatusCode(final int expectedStatusCode) {

        return new HasStatusCodeCondition(expectedStatusCode);
    }

    // < ----------------------- Проверки токена ---------------------------->

    @Step("Проверка различия токена при повторной авторизации пользователя")
    public static HasDifferentTokenCondition hasDifferentToken(final String firstToken) {

        return new HasDifferentTokenCondition(firstToken);
    }

    // < ----------------------- Проверки пользователя ----------------- >
    @Step("Проверка id пользователя")
    public static HasUserIdCondition hasIDUser() {

        return new HasUserIdCondition();
    }

    @Step("Проверка JWT токена")
    public static HasTokenCondition hasToken() {

        return new HasTokenCondition();
    }

    @Step("Проверка логина")
    public static HasUserLoginCondition hasLogin() {

        return new HasUserLoginCondition();
    }

    @Step("Проверка списка с последними зарегистрированными пользователями")
    public static HasRegisteredLoginCondition hasRegisteredLogin(final String expectedLogin) {

        return new HasRegisteredLoginCondition(expectedLogin);
    }

    // < ----------------------- Файловые проверки ----------------- >
    @Step("Проверка скаченного файла")
    public static HasDownloadedImageCondition hasDownloadedImage() {

        return new HasDownloadedImageCondition();
    }

    @Step("Проверка последнего загруженного файла")
    public static HasLastUploadedFileCondition hasLastUploadedFile() {

        return new HasLastUploadedFileCondition();
    }

    // < -------------------- Проверки игр -------------------------- >
    @Step("Проверка наличия игр")
    public static HasGamesCondition hasGames() {

        return new HasGamesCondition();
    }

    @Step("Проверка поля gameId")
    public static HasGameIdCondition hasGameId() {

        return new HasGameIdCondition();
    }

    @Step("Проверка поля title")
    public static HasGameTitleCondition hasTitle() {

        return new HasGameTitleCondition();
    }

    @Step("Проверка поля genre")
    public static HasGameGenreCondition hasGenre() {

        return new HasGameGenreCondition();
    }

    @Step("Проверка поля requiredAge")
    public static HasGameRequiredAgeCondition hasRequiredAge() {

        return new HasGameRequiredAgeCondition();
    }

    @Step("Проверка поля isFree")
    public static HasGameIsFreeCondition hasIsFree() {

        return new HasGameIsFreeCondition();
    }

    @Step("Проверка поля price")
    public static HasGamePriceCondition hasPrice() {

        return new HasGamePriceCondition();
    }

    @Step("Проверка поля company")
    public static HasGameCompanyCondition hasCompany() {

        return new HasGameCompanyCondition();
    }

    @Step("Проверка поля publish_date")
    public static HasGamePublishDateCondition hasPublishDate() {

        return new HasGamePublishDateCondition();
    }

    @Step("Проверка поля rating")
    public static HasGameRatingCondition hasRating() {

        return new HasGameRatingCondition();
    }

    @Step("Проверка поля description")
    public static HasGameDescriptionCondition hasDescription() {

        return new HasGameDescriptionCondition();
    }

    @Step("Проверка массива tags")
    public static HasGameTagsCondition hasTags() {

        return new HasGameTagsCondition();
    }

    @Step("Проверка массива dlcs")
    public static HasGameDlcsCondition hasDlcs() {

        return new HasGameDlcsCondition();
    }

    @Step("Проверка объекта similarDlc в массиве dlcs")
    public static HasGameSimilarDlcCondition hasSimilarDlc() {

        return new HasGameSimilarDlcCondition();
    }

    @Step("Проверка объекта requirements")
    public static HasGameRequirementsCondition hasRequirements() {

        return new HasGameRequirementsCondition();
    }

    @Step("Проверка отсутствия списка с играми")
    public static NoGamesCondition hasNoGames() {

        return new NoGamesCondition();
    }

    @Step("Проверка отсутствия DLC")
    public static NoGameDlcsCondition hasNoDlcs() {

        return new NoGameDlcsCondition();
    }

    @Step("Проверка обновления массива dlcs")
    public static HasDifferentSizeGameDlcCondition hasDifferentSizeDlc() {

        return new HasDifferentSizeGameDlcCondition();
    }

    @Step("Проверка обновления поля company")
    public static HasDifferentGameCompanyCondition hasDifferentCompany(final String oldCompany) {

        return new HasDifferentGameCompanyCondition(oldCompany);
    }

    @Step("Проверка обновления поля description")
    public static HasDifferentGameDescriptionCondition hasDifferentDescription(final String oldDescription) {

        return new HasDifferentGameDescriptionCondition(oldDescription);
    }

    @Step("Проверка обновления поля description")
    public static HasDifferentGameGenreCondition hasDifferentGenre(final String oldGenre) {

        return new HasDifferentGameGenreCondition(oldGenre);
    }

    @Step("Проверка обновления поля title")
    public static HasDifferentGameTitleCondition hasDifferentTitle(final String oldTitle) {

        return new HasDifferentGameTitleCondition(oldTitle);
    }

    @Step("Проверка обновления поля price")
    public static HasDifferentGamePriceCondition hasDifferentPrice(final double oldPrice) {

        return new HasDifferentGamePriceCondition(oldPrice);
    }

    @Step("Проверка обновления поля rating")
    public static HasDifferentGameRatingCondition hasDifferentRating(final int oldRating) {

        return new HasDifferentGameRatingCondition(oldRating);
    }

    @Step("Проверка обновления поля isFree")
    public static HasDifferentGameIsFreeCondition hasDifferentIsFree(final boolean oldIsFree) {

        return new HasDifferentGameIsFreeCondition(oldIsFree);
    }

    @Step("Проверка обновления поля requiredAge")
    public static HasDifferentGameRequiredAgeCondition hasDifferentRequiredAge(final boolean oldRequiredAge) {

        return new HasDifferentGameRequiredAgeCondition(oldRequiredAge);
    }

    @Step("Проверка обновления поля publish_date")
    public static HasDifferentGamePublishDateCondition hasDifferentPublishDate(final String oldPublishDate) {

        return new HasDifferentGamePublishDateCondition(oldPublishDate);
    }

    @Step("Проверка количества игр на аккаунте пользователя")
    public static HasTwentyGamesCondition hasTwentyGames() {

        return new HasTwentyGamesCondition();
    }

    // <------------------- Проверка контроллера статус кода ----------------->
    @Step("Проверка описания")
    public static HasDescriptionStatusCodeCondition hasDescription(final String expectedDescription) {

        return new HasDescriptionStatusCodeCondition(expectedDescription);
    }

    // <------------------------- Проверка контроллера тренировки -------------------->
    @Step("Проверка списка машин")
    public static HasCarsCondition hasCars() {

        return new HasCarsCondition();
    }

    @Step("Проверка различных вариантов ключей")
    public static HasVariousKeyCondition hasVariousKey() {

        return new HasVariousKeyCondition();
    }

    @Step("Проверка версии API")
    public static HasApiVersionCondition hasApiVersion(final String expectedApiVersion) {

        return new HasApiVersionCondition(expectedApiVersion);
    }
}
