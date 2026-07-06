package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResponse {
    private String name;
    private Integer height;
    private SpritesResponse sprites;
    private List<AbilityResponse> abilities;
}