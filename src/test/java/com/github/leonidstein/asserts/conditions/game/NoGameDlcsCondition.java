package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.DlcsItemModel;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static com.github.leonidstein.asserts.conditions.Extractor.extractList;
import static org.assertj.core.api.Assertions.assertThat;

public final class NoGameDlcsCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final List<DlcsItemModel> dlc = extractList(response, GamesItemModel.class).getFirst().getDlcs();

        assertThat(dlc).as("Проверка пустого массива dlc")
                       .isEmpty();
    }
}
