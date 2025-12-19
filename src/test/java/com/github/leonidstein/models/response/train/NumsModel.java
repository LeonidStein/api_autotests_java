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
public final class NumsModel {

	@JsonProperty("1")
	private String jsonMember1;

	@JsonProperty("2")
	private String jsonMember2;

	@JsonProperty("3")
	private String jsonMember3;

	@JsonProperty("4")
	private String jsonMember4;
}