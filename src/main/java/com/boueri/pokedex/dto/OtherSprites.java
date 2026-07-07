package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherSprites {

    @JsonProperty("official-artwork")
    private OfficialArtwork officialArtwork;

    private HomeSprites home;

    public OfficialArtwork getOfficialArtwork() {
        return officialArtwork;
    }

    public HomeSprites getHome() {
        return home;
    }
}