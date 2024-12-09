package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.GeneroDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CRUDGenero extends Stage {

    private TableView<GeneroDAO> tbvGenero;
    private TextField txtNombreGenero;
    private Button btnAgregar, btnActualizar, btnEliminar;

    public CRUDGenero() {
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/crudGenero.css").toExternalForm());

        this.setTitle("CRUD Géneros");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        tbvGenero = new TableView<>();
        tbvGenero.getStyleClass().add("table-view");

        TableColumn<GeneroDAO, Integer> colIdGenero = new TableColumn<>("ID");
        colIdGenero.setCellValueFactory(c -> c.getValue().idGeneroProperty().asObject());

        TableColumn<GeneroDAO, String> colNombreGenero = new TableColumn<>("Género");
        colNombreGenero.setCellValueFactory(c -> c.getValue().nombreGeneroProperty());

        tbvGenero.getColumns().addAll(colIdGenero, colNombreGenero);
        tbvGenero.setItems(new GeneroDAO().SELECTALL());

        txtNombreGenero = new TextField();
        txtNombreGenero.setPromptText("Nombre del Género");
        txtNombreGenero.getStyleClass().add("text-field");

        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarGenero());
        btnAgregar.getStyleClass().add("button");

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarGenero());
        btnActualizar.getStyleClass().add("button");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarGenero());
        btnEliminar.getStyleClass().add("button-danger");
    }

    private VBox CrearContenido() {
        CrearUI();

        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        botones.getStyleClass().add("hbox");

        VBox vbox = new VBox(10, tbvGenero, txtNombreGenero, botones);
        vbox.getStyleClass().add("vbox");

        return vbox;
    }

    private void agregarGenero() {
        if (txtNombreGenero.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa el nombre del género.");
            return;
        }

        GeneroDAO genero = new GeneroDAO();
        genero.setNombreGenero(txtNombreGenero.getText());
        genero.INSERT();
        actualizarTabla();
    }

    private void actualizarGenero() {
        GeneroDAO seleccionado = tbvGenero.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un género para actualizar.");
            return;
        }

        seleccionado.setNombreGenero(txtNombreGenero.getText());
        seleccionado.UPDATE();
        actualizarTabla();
    }

    private void eliminarGenero() {
        GeneroDAO seleccionado = tbvGenero.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un género para eliminar.");
            return;
        }

        seleccionado.DELETE();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tbvGenero.setItems(new GeneroDAO().SELECTALL());
        tbvGenero.refresh();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
