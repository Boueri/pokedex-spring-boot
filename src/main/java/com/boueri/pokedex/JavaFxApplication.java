package com.boueri.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    // O main é o ponto de entrada que "lança" a aplicação JavaFX
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        // Inicializa o contexto do Spring Boot
        context = new SpringApplicationBuilder(PokedexApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o layout FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pokedex.fxml"));
        
        // Conecta o Spring aos Controllers (permite usar @Autowired nos controllers)
        loader.setControllerFactory(context::getBean); 
        
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Minha Pokédex Profissional");
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Fecha o contexto do Spring quando a janela for fechada
        context.close();
    }
}