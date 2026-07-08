package com.boueri.pokedex.controller;

import com.boueri.pokedex.client.PokemonClient;
import com.boueri.pokedex.dto.AbilityResponse;
import com.boueri.pokedex.dto.ChainLink;
import com.boueri.pokedex.dto.EvolutionChainResponse;
import com.boueri.pokedex.dto.FlavorTextEntry;
import com.boueri.pokedex.dto.Genus;
import com.boueri.pokedex.dto.PokemonResponse;
import com.boueri.pokedex.dto.PokemonSpeciesResponse;
import com.boueri.pokedex.dto.StatResponse;
import com.boueri.pokedex.dto.TypeResponse;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Controller;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Node;


@Controller
public class PokedexController {

    @FXML private TextField searchField;
    @FXML private Label nameLabel;
    @FXML private Label speciesLabel;
    @FXML private Label generationLabel;
    @FXML private Label habitatLabel;
    @FXML private Label legendaryLabel;
    @FXML private Label mythicalLabel;
    @FXML private Label babyLabel;
    @FXML private Label idLabel;
    @FXML private Label heightLabel;
    @FXML private Label weightLabel;
    @FXML private Label typeLabel;
    @FXML private Label abilitiesLabel;
    @FXML private ImageView pokemonImage;
    @FXML private FlowPane evolutionBox;
    @FXML private VBox statsBox;
    @FXML private Label descriptionLabel;
    @FXML private TextField compareField;
    @FXML private VBox compareBox;

    private final PokemonClient pokemonClient;
    private PokemonResponse pokemonAtual;

    public PokedexController(PokemonClient pokemonClient) {
        this.pokemonClient = pokemonClient;
    }

    @FXML
    public void buscarPokemon() {
        String nome = searchField.getText().trim().toLowerCase();

        if (nome.isEmpty()) {
            limparTela();
            nameLabel.setText("Digite o nome de um Pokémon.");
            return;
         
        }
        
        try {
            PokemonResponse pokemon = pokemonClient.getPokemon(nome);
            pokemonAtual = pokemon;

            if (pokemon == null) {
                limparTela();
                nameLabel.setText("Pokémon não encontrado.");
                return;
            }

            PokemonSpeciesResponse species = pokemonClient.getPokemonSpecies(pokemon.getId());

            carregarSpecies(species);

            nameLabel.setText("#" + pokemon.getId() + " - " + pokemon.getName().toUpperCase());
            idLabel.setText("🆔 ID: " + pokemon.getId());
            heightLabel.setText("📏 Altura: " + (pokemon.getHeight() / 10.0) + " m");
            weightLabel.setText("⚖ Peso: " + (pokemon.getWeight() / 10.0) + " kg");
            descriptionLabel.setText("📘 " + buscarDescricao(species));
            String tipos = montarTipos(pokemon);
            typeLabel.setText("Tipo: " + tipos);
            aplicarCorTipo(tipos);

            abilitiesLabel.setText("✨ Habilidades\n" + montarHabilidades(pokemon));

            carregarImagemPokemon(pokemon);
            carregarStats(pokemon);
            carregarEvolucoesAutomaticas(species);

        } catch (Exception e) {
            limparTela();
            nameLabel.setText("Erro ao buscar Pokémon.");
            e.printStackTrace();
        }
    }

    private void carregarSpecies(PokemonSpeciesResponse species) {
        if (species == null) {
            limparSpecies();
            return;
        }

        speciesLabel.setText("📖 Espécie: não encontrada");

        if (species.getGenera() != null) {
            for (Genus genus : species.getGenera()) {
                if (genus.getLanguage() != null &&
                        genus.getLanguage().getName().equals("en")) {
                    speciesLabel.setText("📖 " + genus.getGenus());
                    break;
                }
            }
        }

        if (species.getGeneration() != null) {
            generationLabel.setText(
                    "🎮 " +
                            species.getGeneration()
                                    .getName()
                                    .replace("-", " ")
                                    .toUpperCase()
            );
        } else {
            generationLabel.setText("🎮 Geração desconhecida");
        }

        if (species.getHabitat() != null) {
            habitatLabel.setText("🌍 Habitat: " + species.getHabitat().getName());
        } else {
            habitatLabel.setText("🌍 Habitat desconhecido");
        }

        legendaryLabel.setText("⭐ Lendário: " + (species.isIs_legendary() ? "Sim" : "Não"));
        mythicalLabel.setText("✨ Mítico: " + (species.isIs_mythical() ? "Sim" : "Não"));
        babyLabel.setText("👶 Bebê: " + (species.isIs_baby() ? "Sim" : "Não"));
    }

    private void limparSpecies() {
        speciesLabel.setText("");
        generationLabel.setText("");
        habitatLabel.setText("");
        legendaryLabel.setText("");
        mythicalLabel.setText("");
        babyLabel.setText("");
    }

    private void carregarImagemPokemon(PokemonResponse pokemon) {
        if (pokemon.getSprites() == null) {
            pokemonImage.setImage(null);
            return;
        }

        String imageUrl = null;

        if (pokemon.getSprites().getOther() != null
                && pokemon.getSprites().getOther().getOfficialArtwork() != null
                && pokemon.getSprites().getOther().getOfficialArtwork().getFrontDefault() != null) {

            imageUrl = pokemon.getSprites()
                    .getOther()
                    .getOfficialArtwork()
                    .getFrontDefault();

        } else if (pokemon.getSprites().getFrontDefault() != null) {
            imageUrl = pokemon.getSprites().getFrontDefault();
        }

        pokemonImage.setImage(imageUrl != null ? new Image(imageUrl) : null);
    }

    private void carregarStats(PokemonResponse pokemon) {

    statsBox.getChildren().clear();

    if (pokemon.getStats() == null) {
        statsBox.getChildren().add(new Label("Sem estatísticas."));
        return;
    }

    for (StatResponse stat : pokemon.getStats()) {

        if (stat.getStat() == null)
            continue;

        String nomeStat = traduzirStat(stat.getStat().getName());
        int valor = stat.getBase_stat();

        // Nome + valor
        Label nome = new Label(nomeStat);
        nome.setMinWidth(120);
        nome.setStyle(
                "-fx-font-size:14px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#222;"
        );

        Label numero = new Label(String.valueOf(valor));
        numero.setMinWidth(35);
        numero.setStyle(
                "-fx-font-size:14px;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#444;"
        );

        Rectangle fundo = new Rectangle(220, 12);
        fundo.setArcWidth(12);
        fundo.setArcHeight(12);
        fundo.setStyle("-fx-fill:#E0E0E0;");

        double largura = Math.min(valor, 150) / 150.0 * 220;

        Rectangle barra = new Rectangle(largura, 12);
        barra.setArcWidth(12);
        barra.setArcHeight(12);
        barra.setStyle("-fx-fill:" + corStat(valor) + ";");

        StackPane barras = new StackPane();
        barras.setAlignment(Pos.CENTER_LEFT);
        barras.getChildren().addAll(fundo, barra);

        HBox linha = new HBox(10);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.getChildren().addAll(nome, numero, barras);

        statsBox.getChildren().add(linha);
    }
}

    private String corStat(int valor) {
        if (valor >= 120) {
            return "#4CAF50";
        } else if (valor >= 80) {
            return "#FFC107";
        } else if (valor >= 50) {
            return "#FF9800";
        } else {
            return "#F44336";
        }
    }

    private String traduzirStat(String stat) {
        switch (stat) {
            case "hp":
                return "HP";
            case "attack":
                return "Ataque";
            case "defense":
                return "Defesa";
            case "special-attack":
                return "Ataque Esp.";
            case "special-defense":
                return "Defesa Esp.";
            case "speed":
                return "Velocidade";
            default:
                return stat;
        }
    }

    private String montarTipos(PokemonResponse pokemon) {
        String tipos = "";

        if (pokemon.getTypes() != null) {
            for (TypeResponse typeResponse : pokemon.getTypes()) {
                if (typeResponse.getType() != null) {
                    tipos += typeResponse.getType().getName() + " ";
                }
            }
        }

        return tipos.trim();
    }

    private String montarHabilidades(PokemonResponse pokemon) {
        String habilidades = "";

        if (pokemon.getAbilities() != null) {
            for (AbilityResponse ability : pokemon.getAbilities()) {
                if (ability.getAbility() != null) {
                    habilidades += "• " + ability.getAbility().getName() + "\n";
                }
            }
        }

        return habilidades;
    }

    private void carregarEvolucoesAutomaticas(PokemonSpeciesResponse species) {
        evolutionBox.getChildren().clear();

        if (species == null || species.getEvolution_chain() == null) {
            evolutionBox.getChildren().add(new Label("Sem linha evolutiva."));
            return;
        }

        EvolutionChainResponse evolutionChain =
                pokemonClient.getEvolutionChain(species.getEvolution_chain().getUrl());

        if (evolutionChain == null || evolutionChain.getChain() == null) {
            evolutionBox.getChildren().add(new Label("Sem linha evolutiva."));
            return;
        }

        adicionarChain(evolutionChain.getChain());
    }

    private void adicionarChain(ChainLink chain) {
        if (chain == null || chain.getSpecies() == null) {
            return;
        }

        adicionarEvolucao(chain.getSpecies().getName());

        if (chain.getEvolves_to() != null) {
            for (ChainLink next : chain.getEvolves_to()) {
                adicionarChain(next);
            }
        }
    }

    private void adicionarEvolucao(String nomePokemon) {
        VBox card = new VBox(6);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(110);

        card.setStyle(
                "-fx-background-color:white;" +
                        "-fx-background-radius:12;" +
                        "-fx-padding:10;" +
                        "-fx-border-color:#dddddd;" +
                        "-fx-border-radius:12;" +
                        "-fx-cursor:hand;"
        );

        ImageView imagem = new ImageView();
        imagem.setFitWidth(72);
        imagem.setFitHeight(72);
        imagem.setPreserveRatio(true);

        try {
            PokemonResponse p = pokemonClient.getPokemon(nomePokemon);

            if (p != null
                    && p.getSprites() != null
                    && p.getSprites().getOther() != null
                    && p.getSprites().getOther().getOfficialArtwork() != null
                    && p.getSprites().getOther().getOfficialArtwork().getFrontDefault() != null) {

                imagem.setImage(new Image(
                        p.getSprites()
                                .getOther()
                                .getOfficialArtwork()
                                .getFrontDefault()
                ));

            } else if (p != null
                    && p.getSprites() != null
                    && p.getSprites().getFrontDefault() != null) {

                imagem.setImage(new Image(p.getSprites().getFrontDefault()));
            }

        } catch (Exception ignored) {
        }

        Label nome = new Label(nomePokemon.toUpperCase());
        nome.setStyle("-fx-font-weight:bold; -fx-font-size:11px;");

        card.getChildren().addAll(imagem, nome);

        card.setOnMouseClicked(event -> {
            searchField.setText(nomePokemon);
            buscarPokemon();
        });

        evolutionBox.getChildren().add(card);
    }

    private void limparTela() {
    limparSpecies();

    idLabel.setText("");
    heightLabel.setText("");
    weightLabel.setText("");
    typeLabel.setText("");
    abilitiesLabel.setText("");
    descriptionLabel.setText("");
    typeLabel.setStyle("");
    pokemonImage.setImage(null);

    if (evolutionBox != null) {
        evolutionBox.getChildren().clear();
    }

    if (statsBox != null) {
        statsBox.getChildren().clear();
    }
}

    private void aplicarCorTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            typeLabel.setStyle("-fx-background-color:#A8A878; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
            return;
        }

        String tipoPrincipal = tipo.split(" ")[0].toLowerCase();

        switch (tipoPrincipal) {
            case "grass":
                typeLabel.setStyle("-fx-background-color:#78C850; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "fire":
                typeLabel.setStyle("-fx-background-color:#F08030; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "water":
                typeLabel.setStyle("-fx-background-color:#6890F0; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "electric":
                typeLabel.setStyle("-fx-background-color:#F8D030; -fx-text-fill:black; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "psychic":
                typeLabel.setStyle("-fx-background-color:#F85888; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "ice":
                typeLabel.setStyle("-fx-background-color:#98D8D8; -fx-text-fill:black; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "dragon":
                typeLabel.setStyle("-fx-background-color:#7038F8; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "dark":
                typeLabel.setStyle("-fx-background-color:#705848; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            case "fairy":
                typeLabel.setStyle("-fx-background-color:#EE99AC; -fx-text-fill:black; -fx-padding:6 12; -fx-background-radius:15;");
                break;
            default:
                typeLabel.setStyle("-fx-background-color:#A8A878; -fx-text-fill:white; -fx-padding:6 12; -fx-background-radius:15;");
        }
    }
    private String buscarDescricao(PokemonSpeciesResponse species) {
    if (species == null || species.getFlavor_text_entries() == null) {
        return "Descrição não encontrada.";
    }

    // 1. Tenta português
    for (FlavorTextEntry entry : species.getFlavor_text_entries()) {
        if (entry.getLanguage() != null &&
                entry.getLanguage().getName().equals("pt-BR")) {

            return limparTexto(entry.getFlavor_text());
        }
    }

    // 2. Tenta português PT
    for (FlavorTextEntry entry : species.getFlavor_text_entries()) {
        if (entry.getLanguage() != null &&
                entry.getLanguage().getName().equals("pt")) {

            return limparTexto(entry.getFlavor_text());
        }
    }

    // 3. Se não tiver, usa inglês
    for (FlavorTextEntry entry : species.getFlavor_text_entries()) {
        if (entry.getLanguage() != null &&
                entry.getLanguage().getName().equals("en")) {

            return limparTexto(entry.getFlavor_text());
        }
    }

    return "Descrição não encontrada.";
}private String limparTexto(String texto) {
    return texto
            .replace("\n", " ")
            .replace("\f", " ")
            .replace("POKéMON", "Pokémon");
}
@FXML
public void mostrarNormal() {
    if (pokemonAtual != null) {
        carregarImagemPokemon(pokemonAtual);
    }
}

@FXML
public void mostrarShiny() {
    if (pokemonAtual == null || pokemonAtual.getSprites() == null) {
        return;
    }

    String shinyUrl = null;

    if (pokemonAtual.getSprites().getOther() != null
            && pokemonAtual.getSprites().getOther().getHome() != null
            && pokemonAtual.getSprites().getOther().getHome().getFrontShiny() != null) {

        shinyUrl = pokemonAtual.getSprites()
                .getOther()
                .getHome()
                .getFrontShiny();
    }

    if (shinyUrl != null) {
        pokemonImage.setImage(new Image(shinyUrl));
    }
}
@FXML
public void tocarCry() {
    if (pokemonAtual == null ||
            pokemonAtual.getCries() == null) {
        return;
    }

    String cryUrl = pokemonAtual.getCries().getLatest();

    if (cryUrl == null || cryUrl.isBlank()) {
        cryUrl = pokemonAtual.getCries().getLegacy();
    }

    if (cryUrl == null || cryUrl.isBlank()) {
        return;
    }

    Media media = new Media(cryUrl);
    MediaPlayer player = new MediaPlayer(media);
    player.play();
}
@FXML
public void compararPokemon() {

    compareBox.getChildren().clear();

    if (pokemonAtual == null) {
        compareBox.getChildren().add(criarTextoComparador("Pesquise um Pokémon primeiro."));
        return;
    }

    String nome = compareField.getText().trim().toLowerCase();

    if (nome.isBlank()) {
        compareBox.getChildren().add(criarTextoComparador("Digite outro Pokémon para comparar."));
        return;
    }

    PokemonResponse rival = pokemonClient.getPokemon(nome);

    if (rival == null) {
        compareBox.getChildren().add(criarTextoComparador("Pokémon não encontrado."));
        return;
    }

    HBox topo = new HBox(40);
    topo.setAlignment(Pos.CENTER);

    VBox cardAtual = criarCardComparacao(pokemonAtual);
    VBox cardRival = criarCardComparacao(rival);

    Label versus = new Label("VS");
    versus.setStyle("-fx-font-size:28px; -fx-font-weight:bold; -fx-text-fill:#E53935;");

    topo.getChildren().addAll(cardAtual, versus, cardRival);
    compareBox.getChildren().add(topo);

    int pontosAtual = 0;
    int pontosRival = 0;

    for (int i = 0; i < pokemonAtual.getStats().size(); i++) {

        StatResponse s1 = pokemonAtual.getStats().get(i);
        StatResponse s2 = rival.getStats().get(i);

        int valor1 = s1.getBase_stat();
        int valor2 = s2.getBase_stat();

        if (valor1 > valor2) pontosAtual++;
        if (valor2 > valor1) pontosRival++;

        compareBox.getChildren().add(
                criarLinhaComparacao(
                        traduzirStat(s1.getStat().getName()),
                        valor1,
                        valor2
                )
        );
    }

    Label resultado = new Label();

    if (pontosAtual > pontosRival) {
        resultado.setText("🏆 Vencedor Geral: " + pokemonAtual.getName().toUpperCase());
    } else if (pontosRival > pontosAtual) {
        resultado.setText("🏆 Vencedor Geral: " + rival.getName().toUpperCase());
    } else {
        resultado.setText("🤝 Empate Geral");
    }

    resultado.setStyle("-fx-font-size:22px; -fx-font-weight:bold; -fx-text-fill:#222;");
    compareBox.getChildren().add(resultado);
    }

    private Label criarTextoComparador(String texto) {
    Label label = new Label(texto);
    label.setStyle("-fx-font-size:16px; -fx-text-fill:#222;");
    return label;
}

private VBox criarCardComparacao(PokemonResponse pokemon) {

    VBox card = new VBox(8);
    card.setAlignment(Pos.CENTER);
    card.setStyle(
            "-fx-background-color:#F7F7F7;" +
            "-fx-background-radius:18;" +
            "-fx-padding:16;"
    );

    ImageView imagem = new ImageView();
    imagem.setFitWidth(130);
    imagem.setFitHeight(130);
    imagem.setPreserveRatio(true);

    String imageUrl = pegarUrlImagem(pokemon);

    if (imageUrl != null) {
        imagem.setImage(new Image(imageUrl));
    }

    Label nome = new Label(pokemon.getName().toUpperCase());
    nome.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-text-fill:#222;");

    Label tipo = new Label(montarTipos(pokemon));
    tipo.setStyle("-fx-font-size:14px; -fx-text-fill:#555;");

    card.getChildren().addAll(imagem, nome, tipo);

    return card;
}

private HBox criarLinhaComparacao(String nomeStat, int valor1, int valor2) {

    Label stat = new Label(nomeStat);
    stat.setMinWidth(110);
    stat.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-text-fill:#222;");

    Label numero1 = new Label(String.valueOf(valor1));
    numero1.setMinWidth(35);
    numero1.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-text-fill:" +
            (valor1 >= valor2 ? "#2E7D32" : "#777") + ";");

    StackPane barra1 = criarBarraComparacao(valor1);

    Label numero2 = new Label(String.valueOf(valor2));
    numero2.setMinWidth(35);
    numero2.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-text-fill:" +
            (valor2 >= valor1 ? "#2E7D32" : "#777") + ";");

    StackPane barra2 = criarBarraComparacao(valor2);

    HBox linha = new HBox(10);
    linha.setAlignment(Pos.CENTER_LEFT);
    linha.getChildren().addAll(numero1, barra1, stat, barra2, numero2);

    return linha;
}

private StackPane criarBarraComparacao(int valor) {

    Rectangle fundo = new Rectangle(180, 12);
    fundo.setArcWidth(12);
    fundo.setArcHeight(12);
    fundo.setStyle("-fx-fill:#E0E0E0;");

    Rectangle barra = new Rectangle(Math.min(valor, 150) / 150.0 * 180, 12);
    barra.setArcWidth(12);
    barra.setArcHeight(12);
    barra.setStyle("-fx-fill:" + corStat(valor) + ";");

    StackPane box = new StackPane();
    box.setAlignment(Pos.CENTER_LEFT);
    box.getChildren().addAll(fundo, barra);

    return box;
}

private String pegarUrlImagem(PokemonResponse pokemon) {

    if (pokemon == null || pokemon.getSprites() == null) {
        return null;
    }

    if (pokemon.getSprites().getOther() != null
            && pokemon.getSprites().getOther().getOfficialArtwork() != null
            && pokemon.getSprites().getOther().getOfficialArtwork().getFrontDefault() != null) {

        return pokemon.getSprites()
                .getOther()
                .getOfficialArtwork()
                .getFrontDefault();
    }

    return pokemon.getSprites().getFrontDefault();
}
}