package com.github.leonidstein.constants.info;

import lombok.Getter;

@Getter
public enum Message {

    // < ----------------------------- USER ---------------------------------->
    USER_CREATED("User created"),

    LOGIN_ALREADY_EXIST("Login already exist"),

    MISSING_LOGIN_OR_PASSWORD("Missing login or password"),

    USER_SUCCESSFULLY_DELETED("User successfully deleted"),

    USER_PASSWORD_SUCCESSFULLY_CHANGED("User password successfully changed"),

    BODY_HAS_NO_PASSWORD_PARAMETER("Body has no password parameter"),
    // <------------------------------ FILE --------------------------->,
    FILE_UPLOADED_TO_SERVER("file uploaded to server"),

    // <------------------------------ GAME -------------------------->
    GAME_CREATED("Game created"),

    GAME_WITH_THIS_ID_NOT_EXIST("Game with this id not exist"),

    GAME_SUCCESSFULLY_DELETED("Game successfully deleted"),

    LIST_WITH_DLC_TO_DELETE_CANT_BE_EMPTY_OR_NULL("List with DLC to delete cant be empty or null"),

    GAME_DLC_SUCCESSFULLY_DELETED("Game DLC successfully deleted"),

    DLC_SUCCESSFULLY_CHANGED("DlC successfully changed"),

    EMPTY_BODY_WITH_LIST_OF_DLC_TO_MODIFY("Empty body with list of dlc to modify"),

    LIMIT_OF_GAMES_USER_CAN_HAVE_ONLY_TWENTY_GAMES("Limit of games, user can have only 20 games"),

    FREE_DLC_OR_GAME_CANT_HAVE_PRICE_MORE_THAN_ZERO("Free DLC or Game cant have price more than 0.0$"),

    // < ----------------------------- UPDATE FIELD GAME ---------------------------->
    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_COMPANY("New value edited successfully on field company"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_DESCRIPTION("New value edited successfully on field description"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_GENRE("New value edited successfully on field genre"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_TITLE("New value edited successfully on field title"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_PRICE("New value edited successfully on field price"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_RATING("New value edited successfully on field rating"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_IS_FREE("New value edited successfully on field isFree"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_REQUIRED_AGE("New value edited successfully on field requiredAge"),

    NEW_VALUE_EDITED_SUCCESSFULLY_ON_FIELD_PUBLISH_DATE("New value edited successfully on field publish_date"),

    CANNOT_EDIT_ID_FIELD("Cannot edit ID field"),

    CANNOT_SET_NEW_VALUE_BECAUSE_FIELD_HAS_INCORRECT_TYPE("Cannot set new value because field has incorrect type");

    private final String message;

    Message(String message) {
        this.message = message;
    }
}
