package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.ArtistaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CRUDArtistas extends Stage {

    private TableView<ArtistaDAO> tbvArtistas;
    private TextField txtNombre;
    private Button btnAgregar, btnActualizar, btnEliminar;

    public CRUDArtistas() {
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/crudArtistas.css").toExternalForm());

        this.setTitle("CRUD Artistas");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        tbvArtistas = new TableView<>();
        tbvArtistas.getStyleClass().add("table-view");

        TableColumn<ArtistaDAO, String> colNombre = new TableColumn<>("Nombre del Artista");
        colNombre.setCellValueFactory(c -> c.getValue().nombreArtistaProperty());
        tbvArtistas.getColumns().add(colNombre);
        tbvArtistas.setItems(new ArtistaDAO().SELECTALL());

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Artista");
        txtNombre.getStyleClass().add("text-field");

        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarArtista());
        btnAgregar.getStyleClass().add("button");

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarArtista());
        btnActualizar.getStyleClass().add("button");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarArtista());
        btnEliminar.getStyleClass().add("button");
    }

    private VBox CrearContenido() {
        CrearUI();
        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        VBox vbox = new VBox(10, tbvArtistas, txtNombre, botones);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }

    private void agregarArtista() {
        ArtistaDAO artista = new ArtistaDAO();
        artista.setNombreArtista(txtNombre.getText());
        artista.INSERT();
        actualizarTabla();
    }

    private void actualizarArtista() {
        ArtistaDAO seleccionado = tbvArtistas.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombreArtista(txtNombre.getText());
            seleccionado.UPDATE();
            actualizarTabla();
        }
    }

    private void eliminarArtista() {
        ArtistaDAO seleccionado = tbvArtistas.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.DELETE();
            actualizarTabla();
        }
    }

    private void actualizarTabla() {
        tbvArtistas.setItems(new ArtistaDAO().SELECTALL());
        tbvArtistas.refresh();
    }
}
