package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResponse {

    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;

    private SpritesResponse sprites;

    private List<AbilityResponse> abilities;
    private List<TypeResponse> types;
    private List<StatResponse> stats;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public SpritesResponse getSprites() {
        return sprites;
    }

    public List<AbilityResponse> getAbilities() {
        return abilities;
    }

    public List<TypeResponse> getTypes() {
        return types;
    }

    public List<StatResponse> getStats() {
        return stats;
    }
    private CriesResponse cries;

public CriesResponse getCries() {
    return cries;
}
}