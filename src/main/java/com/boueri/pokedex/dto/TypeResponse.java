package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeResponse {

    private TypeInfo type;

    public TypeInfo getType() {
        return type;
    }
}