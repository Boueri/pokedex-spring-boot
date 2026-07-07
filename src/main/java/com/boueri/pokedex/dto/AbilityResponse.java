package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityResponse {

    private AbilityInfo ability;

    public AbilityInfo getAbility() {
        return ability;
    }

    public void setAbility(AbilityInfo ability) {
        this.ability = ability;
    }
}