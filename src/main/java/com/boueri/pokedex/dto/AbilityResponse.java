package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AbilityResponse {
    private AbilityInfo ability;
}