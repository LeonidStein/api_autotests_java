package com.github.leonidstein.asserts;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.common.token.TokenModel;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import static com.github.leonidstein.asserts.conditions.Extractor.extractClass;
import static com.github.leonidstein.asserts.conditions.Extractor.extractList;

@RequiredArgsConstructor
public final class AssertableResponse {

    private final ValidatableResponse response;

    public AssertableResponse should(Condition condition) {

        condition.check(response);

        return this;
    }

    public AssertableResponse checkResponse() {

        return this;
    }

    public String extractToken() {

        return extractClass(response, TokenModel.class).getToken();
    }

    public Integer extractGameId() {

        return extractList(response, GamesItemModel.class).getFirst().getGameId();
    }
}
