package com.boueri.pokedex.client;

import com.boueri.pokedex.dto.EvolutionChainResponse;
import com.boueri.pokedex.dto.PokemonResponse;
import com.boueri.pokedex.dto.PokemonSpeciesResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PokemonClient {

    private final RestClient restClient;

    public PokemonClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
    }

    public PokemonResponse getPokemon(String name) {
        try {
            return restClient.get()
                    .uri("/pokemon/{name}", name)
                    .retrieve()
                    .body(PokemonResponse.class);
        } catch (Exception e) {
            System.err.println("Erro ao buscar Pokémon: " + e.getMessage());
            return null;
        }
    }

    public PokemonSpeciesResponse getPokemonSpecies(Integer id) {
        try {
            return restClient.get()
                    .uri("/pokemon-species/{id}", id)
                    .retrieve()
                    .body(PokemonSpeciesResponse.class);
        } catch (Exception e) {
            System.err.println("Erro ao buscar species: " + e.getMessage());
            return null;
        }
    }

    public EvolutionChainResponse getEvolutionChain(String url) {
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(EvolutionChainResponse.class);
        } catch (Exception e) {
            System.err.println("Erro ao buscar evolution chain: " + e.getMessage());
            return null;
        }
    }
}