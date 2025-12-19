package com.github.leonidstein.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface TestType {

    Type value();

    enum Type {
        POSITIVE,
        NEGATIVE
    }
}
