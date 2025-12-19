package com.github.leonidstein.asserts.conditions;

import io.restassured.response.ValidatableResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Extractor {

    private final static String ROOT_PATH = "";

    public static <T> List<T> extractList(final ValidatableResponse response, final Class<T> clazz) {

        return response.extract()
                       .body()
                       .jsonPath()
                       .getList(ROOT_PATH, clazz);
    }

    public static <T> List<T> extractList(final ValidatableResponse response, final String path, final Class<T> clazz) {

        return response.extract()
                       .body()
                       .jsonPath()
                       .getList(path, clazz);
    }

    public static <T> List<T> extractListOrSingle(final ValidatableResponse response, final Class<T> clazz) {

        try {
            return extractList(response, clazz);
        } catch (final Exception exception) {
            return List.of(extractObject(response, clazz));
        }
    }

    public static <T> T extractObject(final ValidatableResponse response, final String path, final Class<T> clazz) {

        return response.extract()
                       .body()
                       .jsonPath()
                       .getObject(path, clazz);
    }

    public static <T> T extractObject(final ValidatableResponse response, final Class<T> clazz) {

        return response.extract()
                       .body()
                       .jsonPath()
                       .getObject(ROOT_PATH, clazz);
    }

    public static <T> T extractClass(final ValidatableResponse response, final Class<T> clazz) {

        return response.extract()
                       .body()
                       .as(clazz);
    }

    public static int extractStatusCode(final ValidatableResponse response) {

        return response.extract()
                       .statusCode();
    }

    public static byte[] extractByteArray(final ValidatableResponse response) {

        return response.extract()
                       .body()
                       .asByteArray();
    }
}
