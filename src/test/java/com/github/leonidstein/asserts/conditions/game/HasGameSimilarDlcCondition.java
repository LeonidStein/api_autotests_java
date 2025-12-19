package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import com.github.leonidstein.models.request.user.create.SimilarDlcModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.DLC_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameSimilarDlcCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final SimilarDlcModel similarDlc = extractListOrSingle(response, GamesItemModel.class).getFirst()
                                                                                              .getDlcs().getFirst()
                                                                                              .getSimilarDlc();

        assertThat(similarDlc.getDlcNameFromAnotherGame()).as("Проверка поля dlcNameFromAnotherGame в объекте similarDlc")
                                                          .isNotNull()
                                                          .isNotBlank();
        assertThat(DLC_ARRAY).as("Проверка поля dlcNameFromAnotherGame в объекте similarDlc: " +
                                     "значение должно быть одним из ожидаемых: %s", Arrays.toString(DLC_ARRAY))
                             .contains(similarDlc.getDlcNameFromAnotherGame());

        assertThat(similarDlc.getIsFree()).as("Проверка поля isFree в объекте similarDlc")
                                          .isNotNull();
    }
}
