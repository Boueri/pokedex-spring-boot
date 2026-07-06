package com.boueri.pokedex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokemonService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String buscarPokemon(String nome) {

        String url = 
        "https://pokeapi.co/api/v2/pokemon/" + nome.toLowerCase();

        return restTemplate.getForObject(url, String.class);
    }
}