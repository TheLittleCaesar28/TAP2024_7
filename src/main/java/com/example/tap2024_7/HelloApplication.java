package com.example.tap2024_7;

import com.example.tap2024_7.MODELS.Conexion;
import com.example.tap2024_7.VISTAS.Buscaminas;
import com.example.tap2024_7.VISTAS.Calculadora;
import com.example.tap2024_7.VISTAS.Loteria;
import com.example.tap2024_7.VISTAS.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1;

    @Override
    public void start(Stage stage) {
        CrearUI();
        Scene scene = new Scene(bdpPrincipal, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setTitle("Instituto Tecnológico de Celaya");
        stage.setScene(scene);
        stage.show();
        Conexion.CrearConexion(); // Conectar a la base de datos al inicio
    }

    public void CrearUI() {
        // Menú Calculadora
        MenuItem mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());

        // Menú Lotería
        MenuItem mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(event -> new Loteria());

        // Menú Buscaminas
        MenuItem mitBuscaminas = new MenuItem("Buscaminas");
        mitBuscaminas.setOnAction(event -> new Buscaminas());

        // Menú Spotify
        MenuItem mitSpotify = new MenuItem("Spotify");
        mitSpotify.setOnAction(event -> new Login()); // El Login se muestra al seleccionar Spotify

        // Crear Menú Competencia 1
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria, mitBuscaminas, mitSpotify);

        // Configurar barra de menú principal
        mnbPrincipal = new MenuBar(menCompetencia1);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);
    }

    public static void main(String[] args) {
        launch();
    }
}
