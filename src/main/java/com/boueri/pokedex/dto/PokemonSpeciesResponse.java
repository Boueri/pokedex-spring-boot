package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonSpeciesResponse {

    private EvolutionChainUrl evolution_chain;

    private GenerationInfo generation;

    private HabitatInfo habitat;

    private boolean is_legendary;

    private boolean is_mythical;

    private boolean is_baby;

    private List<Genus> genera;

    public EvolutionChainUrl getEvolution_chain() {
        return evolution_chain;
    }

    public GenerationInfo getGeneration() {
        return generation;
    }

    public HabitatInfo getHabitat() {
        return habitat;
    }

    public boolean isIs_legendary() {
        return is_legendary;
    }

    public boolean isIs_mythical() {
        return is_mythical;
    }

    public boolean isIs_baby() {
        return is_baby;
    }

    public List<Genus> getGenera() {
        return genera;
    }
private List<FlavorTextEntry> flavor_text_entries;

public List<FlavorTextEntry> getFlavor_text_entries() {
    return flavor_text_entries;
}
}