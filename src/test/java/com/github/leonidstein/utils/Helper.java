package com.github.leonidstein.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Helper {

    @SneakyThrows
    public static String findByJsonProperty(final String jsonKey, final Class<?> clazz) {

        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(field -> {
                         final JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);

                         return jsonProperty != null && jsonProperty.value().equals(jsonKey);
                     })
                     .findFirst()
                     .orElseThrow(() -> new NoSuchFieldException("Не найдено поле с аннотацией @JsonProperty = " + jsonKey))
                     .getName();
    }

    @SneakyThrows
    public static File getFileFromResources(final String path) {

        return new File(Objects.requireNonNull(
                                       Helper.class.getClassLoader().getResource(path))
                               .toURI()
        );
    }

}
