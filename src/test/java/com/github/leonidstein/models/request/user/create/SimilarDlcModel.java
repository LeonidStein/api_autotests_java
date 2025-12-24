package com.github.leonidstein.models.request.user.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class SimilarDlcModel {

    @JsonProperty("dlcNameFromAnotherGame")
    private String dlcNameFromAnotherGame;

    @JsonProperty("isFree")
    private Boolean isFree;
}