package com.github.leonidstein.services;

import com.github.leonidstein.asserts.AssertableResponse;
import com.github.leonidstein.utils.annotations.GET;
import io.qameta.allure.Step;

import static com.github.leonidstein.constants.endpoint.ResponseTrainEndpoint.GET_CARS;
import static com.github.leonidstein.constants.endpoint.ResponseTrainEndpoint.GET_CURRENT_VERSION;
import static com.github.leonidstein.constants.endpoint.ResponseTrainEndpoint.GET_VARIOUS_KEY;
import static com.github.leonidstein.constants.endpoint.ResponseTrainEndpoint.REDIRECTION;

public final class TrainService extends BaseService implements Requestable<TrainService> {

    @Override
    public TrainService makeRequest() {

        return this;
    }

    @GET
    @Step("Получение списка с машинами")
    public AssertableResponse getListWithCars() {

        return makeGetRequest(GET_CARS.getEndpoint());
    }

    @GET
    @Step("Получение различных вариантов ключей")
    public AssertableResponse getVariousKey() {

        return makeGetRequest(GET_VARIOUS_KEY.getEndpoint());
    }

    @GET
    @Step("Перенаправление на определенный адрес")
    public AssertableResponse getRedirect() {

        final boolean isRedirected = true;

        return makeGetRequest(REDIRECTION.getEndpoint(), isRedirected);
    }

    @GET
    @Step("Получение актуальной версии API приложения")
    public AssertableResponse getCurrentApiAppVersion() {

        return makeGetRequest(GET_CURRENT_VERSION.getEndpoint());
    }
}
