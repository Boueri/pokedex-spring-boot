package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChainLink {

    private SpeciesInfo species;
    private List<ChainLink> evolves_to;

    public SpeciesInfo getSpecies() {
        return species;
    }

    public List<ChainLink> getEvolves_to() {
        return evolves_to;
    }
}