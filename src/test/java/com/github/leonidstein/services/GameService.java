package com.github.leonidstein.services;

import com.github.leonidstein.asserts.AssertableResponse;
import com.github.leonidstein.models.request.field.GameFieldsModel;
import com.github.leonidstein.models.request.user.create.DlcsItemModel;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import com.github.leonidstein.utils.annotations.DELETE;
import com.github.leonidstein.utils.annotations.GET;
import com.github.leonidstein.utils.annotations.POST;
import com.github.leonidstein.utils.annotations.PUT;
import io.qameta.allure.Step;

import java.util.List;

import static com.github.leonidstein.constants.endpoint.GameEndpoint.ADD_GAMES;
import static com.github.leonidstein.constants.endpoint.GameEndpoint.DELETE_DLC;
import static com.github.leonidstein.constants.endpoint.GameEndpoint.DELETE_GAME;
import static com.github.leonidstein.constants.endpoint.GameEndpoint.GET_GAME;
import static com.github.leonidstein.constants.endpoint.GameEndpoint.GET_GAMES;
import static com.github.leonidstein.constants.endpoint.GameEndpoint.UPDATE_GAME_DLC_INFO;
import static com.github.leonidstein.constants.endpoint.GameEndpoint.UPDATE_GAME_FIELD;
import static com.github.leonidstein.constants.endpoint.GamePath.GAME_ID;
import static com.github.leonidstein.constants.endpoint.GamePath.ID;

public final class GameService extends BaseService implements Requestable<GameService> {

    @Override
    public GameService makeRequest() {

        return this;
    }

    @GET
    @Step("Получение списка с играми у пользователя")
    public AssertableResponse getGames(final String token) {

        return makeGetRequest(GET_GAMES.getEndpoint(), token);
    }

    @GET
    @Step("Получение списка с играми у пользователя без токена авторизации")
    public AssertableResponse getGamesWithoutToken() {

        return makeGetRequest(GET_GAMES.getEndpoint());
    }

    @GET
    @Step("Получение игры у пользователя по ID игры")
    public AssertableResponse getGameByGameId(final String token, final Integer gameId) {

        return makeGetRequest(GET_GAME.getEndpoint(), ID.getPath(), token, String.valueOf(gameId));
    }

    @GET
    @Step("Получение игры у пользователя по ID игры без токена авторизации")
    public AssertableResponse getGameByGameIdWithoutToken(final Integer gameId) {

        return makeGetRequest(GET_GAME.getEndpoint(), ID.getPath(), String.valueOf(gameId));
    }

    @DELETE
    @Step("Удаление игры по её ID у пользователя")
    public AssertableResponse deleteGameByGameId(final String token, final Integer gameId) {

        return makeDeleteRequest(DELETE_GAME.getEndpoint(), ID.getPath(), token, String.valueOf(gameId));
    }

    @DELETE
    @Step("Удаление игры по её ID у пользователя без токена авторизации")
    public AssertableResponse deleteGameByGameIdWithoutToken(final Integer id) {

        return makeDeleteRequest(DELETE_GAME.getEndpoint(), ID.getPath(), String.valueOf(id));
    }

    @DELETE
    @Step("Удаление dlc игры по ID игры у пользователя без указания конкретного dlc")
    public AssertableResponse deleteDlcByGameIdWithoutDlc(final String token, final Integer gameId) {

        return makeDeleteRequest(DELETE_DLC.getEndpoint(), ID.getPath(), token, String.valueOf(gameId));
    }

    @DELETE
    @Step("Удаление dlc игры по ID игры у пользователя с указанием конкретного dlc")
    public AssertableResponse deleteDlcByGameIdWithDlc(final String token, final Integer gameId,
            final List<DlcsItemModel> dlcsList) {

        return makeDeleteRequest(DELETE_DLC.getEndpoint(), ID.getPath(), token, String.valueOf(gameId), dlcsList);
    }

    @DELETE
    @Step("Удаление игры по её ID у пользователя без токена авторизации")
    public AssertableResponse deleteDlcByGameIdWithoutToken(final Integer gameId, final List<DlcsItemModel> dlcsList) {

        return makeDeleteRequest(DELETE_DLC.getEndpoint(), ID.getPath(), String.valueOf(gameId), dlcsList);
    }

    @PUT
    @Step("Полное обновление списка DLC у игры с указанием конкретного dlc")
    public AssertableResponse updateDlcInfo(final String token, final Integer gameId,
            final List<DlcsItemModel> dlcsList) {

        return makePutRequest(UPDATE_GAME_DLC_INFO.getEndpoint(), GAME_ID.getPath(), token, String.valueOf(gameId),
                dlcsList);
    }

    @PUT
    @Step("Полное обновление списка DLC у игры с указанием конкретного dlc без токена авторизации")
    public AssertableResponse updateDlcInfoWithoutToken(final Integer gameId, final List<DlcsItemModel> dlcsList) {

        return makePutRequest(UPDATE_GAME_DLC_INFO.getEndpoint(), GAME_ID.getPath(), String.valueOf(gameId), dlcsList);
    }

    @PUT
    @Step("Полное обновление списка DLC у игры без указания конкретного dlc")
    public AssertableResponse updateDlcInfoWithoutDlc(final String token, final Integer gameId) {

        return makePutRequest(UPDATE_GAME_DLC_INFO.getEndpoint(), GAME_ID.getPath(), token, String.valueOf(gameId));
    }

    @PUT
    @Step("Обновление поля у игры")
    public AssertableResponse updateGameField(final String token, final Integer gameId,
            final GameFieldsModel updateGameField) {

        return makePutRequest(UPDATE_GAME_FIELD.getEndpoint(), GAME_ID.getPath(),
                token, String.valueOf(gameId), updateGameField);
    }

    @POST
    @Step("Добавление новой игры пользователю")
    public AssertableResponse addNewGame(final String token, final GamesItemModel game) {

        return makePostRequest(ADD_GAMES.getEndpoint(), token, game);
    }

    @POST
    @Step("Добавление новой игры пользователю без токена авторизации")
    public AssertableResponse addNewGameWithoutToken(final GamesItemModel game) {

        return makePostRequest(ADD_GAMES.getEndpoint(), game);
    }

    @POST
    @Step("Добавление новой игры пользователю без токена авторизации")
    public AssertableResponse addNewGameWithoutGame(final String token) {

        return makePostRequest(ADD_GAMES.getEndpoint(), token);
    }
}
