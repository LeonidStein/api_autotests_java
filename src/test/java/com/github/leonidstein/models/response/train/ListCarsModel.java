package com.github.leonidstein.models.response.train;

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
public final class ListCarsModel {

    @JsonProperty("models")
    private List<String> models;

    @JsonProperty("brand")
    private String brand;
}