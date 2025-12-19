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
public final class DlcsItemModel {

    @JsonProperty("description")
    private String description;

    @JsonProperty("dlcName")
    private String dlcName;

    @JsonProperty("isDlcFree")
    private Boolean isDlcFree;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("similarDlc")
    private SimilarDlcModel similarDlc;
}