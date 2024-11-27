package com.example.tap2024_7.VISTAS;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SpotifyDashboard extends Stage {

    public SpotifyDashboard() {
        VBox vbox = new VBox(15);
        vbox.getStyleClass().add("vbox");

        Button btnUsuarios = new Button("Gestionar Usuarios");
        btnUsuarios.getStyleClass().add("button");
        btnUsuarios.setOnAction(event -> new CRUDUsuarios());

        Button btnAlbumes = new Button("Gestionar Álbumes");
        btnAlbumes.getStyleClass().add("button");
        btnAlbumes.setOnAction(event -> new CRUDAlbumes());

        Button btnCanciones = new Button("Gestionar Canciones");
        btnCanciones.getStyleClass().add("button");
        btnCanciones.setOnAction(event -> new CRUDCanciones());

        Button btnArtistas = new Button("Gestionar Artistas");
        btnArtistas.getStyleClass().add("button");
        btnArtistas.setOnAction(event -> new CRUDArtistas());

        Button btnEstadisticas = new Button("Estadísticas");
        btnEstadisticas.getStyleClass().add("button");
        btnEstadisticas.setOnAction(event -> new Estadisticas());

        vbox.getChildren().addAll(btnUsuarios, btnAlbumes, btnCanciones, btnArtistas, btnEstadisticas);

        Scene scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());

        this.setScene(scene);
        this.setTitle("Dashboard - Spotify");
        this.show();
    }
}
