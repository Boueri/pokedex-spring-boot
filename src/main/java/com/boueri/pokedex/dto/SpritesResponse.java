package com.boueri.pokedex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpritesResponse {

    @JsonProperty("front_default")
    private String frontDefault;
    
    // Se você quiser mais imagens, pode adicionar aqui:
    // @JsonProperty("back_default")
    // private String backDefault;
}