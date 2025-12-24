package com.github.leonidstein.builders;

import com.github.leonidstein.models.request.field.GameFieldsModel;
import com.github.leonidstein.models.request.user.create.GamesItemModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.leonidstein.data.FakeDataManager.getCompanyForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getDescriptionForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getGenreForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getPriceForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getPublishDateForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getRandomIntFromTo;
import static com.github.leonidstein.data.FakeDataManager.getRatingForUpdate;
import static com.github.leonidstein.data.FakeDataManager.getTitleForUpdate;
import static com.github.leonidstein.utils.Helper.findByJsonProperty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameFieldBuilder {

    private final static String COMPANY_FIELD = "company";
    private final static String DESCRIPTION_FIELD = "description";
    private final static String GENRE_FIELD = "genre";
    private final static String TITLE_FIELD = "title";
    private final static String GAME_PUBLISH_DATE = "publish_date";
    private final static String GAME_ID_FIELD = "gameId";
    private final static String GAME_PRICE = "price";
    private final static String GAME_RATING = "rating";
    private final static String GAME_IS_FREE = "isFree";
    private final static String GAME_REQUIRED_AGE = "requiredAge";

    public static GameFieldsModel buildUpdateGameField(final String field, final Object value) {

        return GameFieldsModel.builder()
                                .fieldName(field)
                                .value(value)
                              .build();
    }

    public static GameFieldsModel updateCompanyField() {

        return buildUpdateGameField(findByJsonProperty(COMPANY_FIELD, GamesItemModel.class), getCompanyForUpdate());
    }

    public static GameFieldsModel updateDescriptionField() {

        return buildUpdateGameField(findByJsonProperty(DESCRIPTION_FIELD, GamesItemModel.class), getDescriptionForUpdate());
    }

    public static GameFieldsModel updateGameGenreField() {

        return buildUpdateGameField(findByJsonProperty(GENRE_FIELD, GamesItemModel.class), getGenreForUpdate());
    }

    public static GameFieldsModel updateTitleField() {

        return buildUpdateGameField(findByJsonProperty(TITLE_FIELD, GamesItemModel.class), getTitleForUpdate());
    }

    public static GameFieldsModel updateGameIdField() {

        return buildUpdateGameField(findByJsonProperty(GAME_ID_FIELD, GamesItemModel.class), getRandomIntFromTo(1, Integer.MAX_VALUE));
    }

    public static GameFieldsModel updateGamePriceField() {

        return buildUpdateGameField(findByJsonProperty(GAME_PRICE, GamesItemModel.class), getPriceForUpdate());
    }

    public static GameFieldsModel updateGameRatingField() {

        return buildUpdateGameField(findByJsonProperty(GAME_RATING, GamesItemModel.class), getRatingForUpdate());
    }

    public static GameFieldsModel updateGameIsFreeField() {

        return buildUpdateGameField(findByJsonProperty(GAME_IS_FREE, GamesItemModel.class), true);
    }

    public static GameFieldsModel updateGameRequiredAgeField() {

        return buildUpdateGameField(findByJsonProperty(GAME_REQUIRED_AGE, GamesItemModel.class), true);
    }

    public static GameFieldsModel updateGamePublishDateField() {

        return buildUpdateGameField(findByJsonProperty(GAME_PUBLISH_DATE, GamesItemModel.class), getPublishDateForUpdate());
    }
}
