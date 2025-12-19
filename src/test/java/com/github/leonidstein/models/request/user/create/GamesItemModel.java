package com.github.leonidstein.models.request.user.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class GamesItemModel {

    @Getter
    @JsonProperty("company")
    private String company;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dlcs")
    private List<DlcsItemModel> dlcs;

    @JsonProperty("gameId")
    private Integer gameId;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("isFree")
    private Boolean isFree;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("requiredAge")
    private Boolean requiredAge;

    @JsonProperty("requirements")
    private RequirementsModel requirements;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("title")
    private String title;
}