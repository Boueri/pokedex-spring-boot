package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EvolutionChainUrl {

    private String url;

    public String getUrl() {
        return url;
    }
}