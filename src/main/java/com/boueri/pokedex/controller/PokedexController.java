package com.boueri.pokedex.controller;

import com.boueri.pokedex.client.PokemonClient;
import com.boueri.pokedex.dto.PokemonResponse; // Verifique este import!
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Controller;

@Controller
public class PokedexController {

    @FXML private TextField searchField;
    @FXML private Label nameLabel;
    @FXML private ImageView pokemonImage;

    private final PokemonClient pokemonClient;

    public PokedexController(PokemonClient pokemonClient) {
        this.pokemonClient = pokemonClient;
    }

    @FXML
    public void buscarPokemon() {
        String nome = searchField.getText().toLowerCase();
        PokemonResponse pokemon = pokemonClient.getPokemon(nome);

        if (pokemon != null) {
            nameLabel.setText("Nome: " + pokemon.getName().toUpperCase());
            
            if (pokemon.getSprites() != null && pokemon.getSprites().getFrontDefault() != null) {
                pokemonImage.setImage(new Image(pokemon.getSprites().getFrontDefault()));
            }
        }
    }
}