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
public final class RequirementsModel {

    @JsonProperty("hardDrive")
    private Integer hardDrive;

    @JsonProperty("osName")
    private String osName;

    @JsonProperty("ramGb")
    private Integer ramGb;

    @JsonProperty("videoCard")
    private String videoCard;
}