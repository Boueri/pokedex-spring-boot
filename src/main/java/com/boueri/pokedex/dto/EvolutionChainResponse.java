package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EvolutionChainResponse {

    private ChainLink chain;

    public ChainLink getChain() {
        return chain;
    }
}