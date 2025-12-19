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
public final class VariousKeyModel {

    @JsonProperty("1")
    private String jsonMember1;

    @JsonProperty("_2")
    private String jsonMember2;

    @JsonProperty("numbersPow")
    private NumbersPowModel numbersPow;

    @JsonProperty("true")
    private Boolean boolTrue;

    @JsonProperty("что то на русском")
    private String somethingInRussian;

    @JsonProperty("bmw:users")
    private String bmwUsers;

    @JsonProperty("'single_quotes'")
    private String singleQuotes;
}