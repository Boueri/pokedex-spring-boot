package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CriesResponse {

    private String latest;
    private String legacy;

    public String getLatest() {
        return latest;
    }

    public String getLegacy() {
        return legacy;
    }
}