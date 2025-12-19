package com.github.leonidstein.models.response.train;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class NumbersPowModel {

    @JsonProperty("nums")
    private NumsModel nums;
}