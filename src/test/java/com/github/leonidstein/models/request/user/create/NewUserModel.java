package com.github.leonidstein.models.request.user.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public final class NewUserModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("games")
    private List<GamesItemModel> games;

    @JsonProperty("login")
    private final String login;

    @JsonProperty("pass")
    private final String pass;
}