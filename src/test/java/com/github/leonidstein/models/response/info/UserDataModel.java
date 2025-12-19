package com.github.leonidstein.models.response.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserDataModel {

    @JsonProperty("pass")
    private String pass;

    @JsonProperty("games")
    private List<Object> games;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("login")
    private String login;
}