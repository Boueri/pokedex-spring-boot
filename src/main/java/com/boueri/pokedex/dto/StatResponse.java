package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatResponse {

    private Integer base_stat;
    private StatInfo stat;

    public Integer getBase_stat() {
        return base_stat;
    }

    public StatInfo getStat() {
        return stat;
    }
}