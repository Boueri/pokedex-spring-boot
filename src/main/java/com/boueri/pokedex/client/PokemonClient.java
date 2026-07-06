package com.boueri.pokedex.client;

import com.boueri.pokedex.dto.PokemonResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PokemonClient {

    private final RestClient restClient;

    public PokemonClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://pokeapi.co/api/v2/pokemon").build();
    }

    public PokemonResponse getPokemon(String name) {
        try {
            return restClient.get()
                    .uri("/{name}", name)
                    .retrieve()
                    .body(PokemonResponse.class);
        } catch (Exception e) {
            System.err.println("Erro ao buscar Pokemon: " + e.getMessage());
            return null;
        }
    }
}