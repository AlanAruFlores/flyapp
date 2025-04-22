package com.flybook.librovuelo.conf;

import java.util.Optional;


public interface EnvironmentUtils {

    static String getSpringProfile() {
        return Optional.ofNullable(System.getenv("ENVIRONMENT"))
                .filter(value -> value.length() != 0)
                .orElse("local");
    }
}
