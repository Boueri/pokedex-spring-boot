package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genus {

    private String genus;

    private Language language;

    public String getGenus() {
        return genus;
    }

    public Language getLanguage() {
        return language;
    }

}