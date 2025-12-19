package com.github.leonidstein.models.response.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class StatusDescriptionModel {

    @JsonProperty("description")
    private String description;

    @JsonProperty("statusCode")
    private Integer statusCode;
}