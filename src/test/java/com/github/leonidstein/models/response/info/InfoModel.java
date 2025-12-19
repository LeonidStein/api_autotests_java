package com.github.leonidstein.models.response.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public final class InfoModel {

    @JsonProperty("info")
    private InfoModel info;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private String status;
}