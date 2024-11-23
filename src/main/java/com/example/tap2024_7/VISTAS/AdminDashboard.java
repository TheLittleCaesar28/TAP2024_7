package com.example.tap2024_7.VISTAS;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard extends Stage {

    public AdminDashboard() {
        VBox vbox = new VBox(10); // Espaciado entre botones
        vbox.getStyleClass().add("vbox"); // Aplica estilo al VBox

        // Botón para CRUD Artistas
        Button btnCRUDArtistas = new Button("CRUD ARTISTAS");
        btnCRUDArtistas.setOnAction(event -> new CRUDArtistas());
        btnCRUDArtistas.getStyleClass().add("button");

        // Botón para CRUD Álbumes
        Button btnCRUDAlbumes = new Button("CRUD ÁLBUMES");
        btnCRUDAlbumes.setOnAction(event -> new CRUDAlbumes());
        btnCRUDAlbumes.getStyleClass().add("button");

        // Botón para CRUD Canciones
        Button btnCRUDCanciones = new Button("CRUD CANCIONES");
        btnCRUDCanciones.setOnAction(event -> new CRUDCanciones());
        btnCRUDCanciones.getStyleClass().add("button");

        // Botón para CRUD Usuarios
        Button btnCRUDUsuarios = new Button("CRUD USUARIOS");
        btnCRUDUsuarios.setOnAction(event -> new CRUDUsuarios());
        btnCRUDUsuarios.getStyleClass().add("button");

        // Botón para Estadísticas
        Button btnEstadisticas = new Button("ESTADÍSTICAS");
        btnEstadisticas.setOnAction(event -> new Estadisticas());
        btnEstadisticas.getStyleClass().add("button");

        // Agregar todos los botones al VBox
        vbox.getChildren().addAll(btnCRUDArtistas, btnCRUDAlbumes, btnCRUDCanciones, btnCRUDUsuarios, btnEstadisticas);

        // Configurar escena con estilos
        Scene scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/adminDashboard.css").toExternalForm());
        this.setScene(scene);

        // Configurar título de la ventana
        this.setTitle("Dashboard - Administrador");
        this.show();
    }
}
