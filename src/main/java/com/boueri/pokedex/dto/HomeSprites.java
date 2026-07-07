package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeSprites {

    @JsonProperty("front_shiny")
    private String frontShiny;

    public String getFrontShiny() {
        return frontShiny;
    }
}