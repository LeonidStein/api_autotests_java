package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.DlcsItemModel;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.DESCRIPTION_ARRAY;
import static com.github.leonidstein.data.FakeData.DLC_ARRAY;
import static com.github.leonidstein.data.FakeData.MAX_PRICE;
import static com.github.leonidstein.data.FakeData.MAX_RATING;
import static com.github.leonidstein.data.FakeData.MIN_PRICE;
import static com.github.leonidstein.data.FakeData.MIN_RATING;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameDlcsCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final DlcsItemModel dlc = extractListOrSingle(response, GamesItemModel.class).getFirst().getDlcs().getFirst();

        assertThat(dlc.getIsDlcFree()).as("Проверка поля isDlcFree в массиве dlcs")
                                      .isNotNull();

        assertThat(dlc.getDlcName()).as("Проверка поля dlcName в массиве dlcs")
                                    .isNotNull()
                                    .isNotBlank();
        assertThat(DLC_ARRAY).as("Проверка поля dlcName в массиве dlcs: значение должно быть одним из ожидаемых: %s",
                                     Arrays.toString(DLC_ARRAY))
                             .contains(dlc.getDlcName());

        assertThat(dlc.getRating()).as("Проверка поля rating в массиве dlcs: rating должен быть в диапозоне от 1 до 100 (вкл)")
                                   .isBetween(MIN_RATING, MAX_RATING);

        assertThat(dlc.getDescription()).as("Проверка поля description в массиве dlcs")
                                        .isNotNull()
                                        .isNotBlank();
        assertThat(DESCRIPTION_ARRAY).as("Проверка поля description в массиве dlcs: значение должно быть одним из ожидаемых: %s",
                                             Arrays.toString(DESCRIPTION_ARRAY))
                                     .contains(dlc.getDescription());

        assertThat(dlc.getPrice()).as("Проверка поля price в массиве dlcs: price должен быть в диапозоне от 1 до 1000 (вкл) ")
                                  .isBetween(MIN_PRICE, MAX_PRICE);
    }
}
