package com.example.tap2024_7;

import com.example.tap2024_7.MODELS.Conexion;
import com.example.tap2024_7.VISTAS.Buscaminas; // Importa la clase Buscaminas
import com.example.tap2024_7.VISTAS.Calculadora;
import com.example.tap2024_7.VISTAS.Loteria;
import com.example.tap2024_7.VISTAS.ListaClientes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1;

    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        Scene scene = new Scene(bdpPrincipal, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setTitle("Instituto Tecnológico de Celaya");
        stage.setScene(scene);
        stage.show();
        Conexion.CrearConexion();
    }

    public void CrearUI() {
        MenuItem mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());

        MenuItem mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(event -> new Loteria());

        MenuItem mitBuscaminas = new MenuItem("Buscaminas"); // Nuevo item de menú para Buscaminas
        mitBuscaminas.setOnAction(event -> new Buscaminas()); // Asigna la acción para abrir Buscaminas

        MenuItem mitSpotify = new MenuItem("Spotify");
        mitSpotify.setOnAction(event -> new ListaClientes());


        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria,mitBuscaminas ,mitSpotify); // Añade Buscaminas al menú

        mnbPrincipal = new MenuBar(menCompetencia1);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);
    }

    public static void main(String[] args) {
        launch();
    }
}
