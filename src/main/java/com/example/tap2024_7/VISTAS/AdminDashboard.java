package com.example.tap2024_7.VISTAS;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard extends Stage {

    public AdminDashboard() {
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");

        Button btnCRUDArtistas = new Button("CRUD ARTISTAS");
        btnCRUDArtistas.setOnAction(event -> new CRUDArtistas());
        btnCRUDArtistas.getStyleClass().add("button");

        Button btnCRUDAlbumes = new Button("CRUD ÁLBUMES");
        btnCRUDAlbumes.setOnAction(event -> new CRUDAlbumes());
        btnCRUDAlbumes.getStyleClass().add("button");

        Button btnCRUDCanciones = new Button("CRUD CANCIONES");
        btnCRUDCanciones.setOnAction(event -> new CRUDCanciones());
        btnCRUDCanciones.getStyleClass().add("button");

        Button btnCRUDUsuarios = new Button("CRUD USUARIOS");
        btnCRUDUsuarios.setOnAction(event -> new CRUDUsuarios());
        btnCRUDUsuarios.getStyleClass().add("button");

        Button btnCRUDGenero = new Button("CRUD GENEROS");
        btnCRUDGenero.getStyleClass().add("button");
        btnCRUDGenero.setOnAction(event -> new CRUDGenero());

        Button btnEstadisticas = new Button("ESTADÍSTICAS");
        btnEstadisticas.setOnAction(event -> new Estadisticas());
        btnEstadisticas.getStyleClass().add("button");

        vbox.getChildren().addAll(btnCRUDArtistas, btnCRUDAlbumes, btnCRUDCanciones, btnCRUDUsuarios, btnCRUDGenero, btnEstadisticas);

        Scene scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/adminDashboard.css").toExternalForm());
        this.setScene(scene);

        this.setTitle("Dashboard - Administrador");
        this.show();
    }
}