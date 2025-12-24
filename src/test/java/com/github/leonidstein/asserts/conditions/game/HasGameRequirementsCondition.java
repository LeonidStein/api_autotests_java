package com.github.leonidstein.asserts.conditions.game;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import com.github.leonidstein.models.request.user.create.RequirementsModel;
import io.restassured.response.ValidatableResponse;

import java.util.Arrays;

import static com.github.leonidstein.asserts.conditions.Extractor.extractListOrSingle;
import static com.github.leonidstein.data.FakeData.HARD_DRIVE_ARRAY;
import static com.github.leonidstein.data.FakeData.OS_NAME_ARRAY;
import static com.github.leonidstein.data.FakeData.RAM_GB_ARRAY;
import static com.github.leonidstein.data.FakeData.VIDEO_CARD_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasGameRequirementsCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final RequirementsModel requirements = extractListOrSingle(response, GamesItemModel.class).getFirst().getRequirements();

        assertThat(requirements.getOsName()).as("Проверка поля osName в объекте requirements")
                                            .isNotNull()
                                            .isNotBlank();
        assertThat(OS_NAME_ARRAY).as("Проверка поля osName в объекте requirements: значение должно быть одним из ожидаемых: %s",
                                         Arrays.toString(OS_NAME_ARRAY))
                                 .contains(requirements.getOsName());

        assertThat(requirements.getRamGb()).as("Проверка поля ramGb в объекте requirements")
                                           .isNotNull();
        assertThat(RAM_GB_ARRAY).as("Проверка поля ramGb в объекте requirements: значение должно быть одним из ожидаемых: %s",
                                        Arrays.toString(RAM_GB_ARRAY))
                                .contains(requirements.getRamGb());

        assertThat(requirements.getHardDrive()).as("Проверка поля hardDrive в объекте requirements")
                                               .isNotNull();
        assertThat(HARD_DRIVE_ARRAY).as("Проверка поля hardDrive в объекте requirements: значение должно быть одним из ожидаемых: %s",
                                            Arrays.toString(HARD_DRIVE_ARRAY))
                                    .contains(requirements.getHardDrive());

        assertThat(requirements.getVideoCard()).as("Проверка поля videoCard в объекте requirements")
                                               .isNotNull()
                                               .isNotBlank();
        assertThat(VIDEO_CARD_ARRAY).as("Проверка поля videoCard в объекте requirements: значение должно быть одним из ожидаемых: %s",
                                            Arrays.toString(VIDEO_CARD_ARRAY))
                                    .contains(requirements.getVideoCard());
    }
}
