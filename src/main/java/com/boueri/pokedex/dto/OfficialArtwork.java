package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficialArtwork {

    @JsonProperty("front_default")
    private String frontDefault;

    public String getFrontDefault() {
        return frontDefault;
    }
}