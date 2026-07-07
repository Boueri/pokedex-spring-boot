package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlavorTextEntry {

    private String flavor_text;
    private Language language;

    public String getFlavor_text() {
        return flavor_text;
    }

    public Language getLanguage() {
        return language;
    }
}