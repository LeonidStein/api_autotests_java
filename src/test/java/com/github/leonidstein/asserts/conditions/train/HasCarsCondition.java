package com.github.leonidstein.asserts.conditions.train;

import com.github.leonidstein.asserts.conditions.Condition;
import com.github.leonidstein.models.response.train.ListCarsModel;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static com.github.leonidstein.asserts.conditions.Extractor.extractList;
import static org.assertj.core.api.Assertions.assertThat;

public final class HasCarsCondition implements Condition {

    @Override
    public void check(final ValidatableResponse response) {

        final List<ListCarsModel> carsItemList = extractList(response, ListCarsModel.class);
        final int expectedCarsInList = 39;

        assertThat(carsItemList).as("Проверка массива машин")
                                .isNotEmpty()
                                .size().isEqualTo(expectedCarsInList);

        carsItemList.forEach(car -> {

            assertThat(car.getBrand()).as("Проверка поля brand")
                                      .isNotNull()
                                      .isNotBlank();
        });
    }

}
