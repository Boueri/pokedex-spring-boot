package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeciesInfo {

    private String name;

    public String getName() {
        return name;
    }
}