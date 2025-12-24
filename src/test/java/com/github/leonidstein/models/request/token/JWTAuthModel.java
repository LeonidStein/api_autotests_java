package com.github.leonidstein.models.request.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class JWTAuthModel {

    @JsonProperty("password")
    private String password;

    @JsonProperty("username")
    private String username;
}