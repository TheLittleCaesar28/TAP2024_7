package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.PlaylistDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CRUDPlaylists extends Stage {

    private TableView<PlaylistDAO> tbvPlaylist;
    private TextField txtNombrePlaylist;
    private Button btnAgregar, btnActualizar, btnEliminar;


    public CRUDPlaylists() {
        VBox contenido = crearContenido();
        Scene scene = new Scene(contenido, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/crudPlaylist.css").toExternalForm());

        this.setTitle("Gestión de Playlist");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {
        // Crear la tabla
        tbvPlaylist = new TableView<>();
        tbvPlaylist.getStyleClass().add("table-view");

        TableColumn<PlaylistDAO, String> colNombre = new TableColumn<>("Nombre de Playlist");
        colNombre.setCellValueFactory(c -> c.getValue().nombrePlaylistProperty());
        colNombre.setPrefWidth(400); // Ajustar el ancho de la columna
        tbvPlaylist.getColumns().add(colNombre);

        // Cargar los datos en la tabla
        tbvPlaylist.setItems(new PlaylistDAO().SELECTALL());

        // Crear el campo de texto
        txtNombrePlaylist = new TextField();
        txtNombrePlaylist.setPromptText("Nombre de Playlist");

        // Crear los botones
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarPlaylist());
        btnAgregar.getStyleClass().add("button");

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarPlaylist());
        btnActualizar.getStyleClass().add("button");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarPlaylist());
        btnEliminar.getStyleClass().add("button-danger");
    }

    private VBox crearContenido() {
        crearUI();

        // Crear la disposición de los botones
        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        botones.getStyleClass().add("hbox");

        // Crear el contenedor principal
        VBox vbox = new VBox(10, tbvPlaylist, txtNombrePlaylist, botones);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }

    private void agregarPlaylist() {
        if (txtNombrePlaylist.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor, introduce un nombre para la playlist.");
            return;
        }

        // Crear nueva playlist y guardarla en la base de datos
        PlaylistDAO playlist = new PlaylistDAO();
        playlist.setNombrePlaylist(txtNombrePlaylist.getText());
        playlist.INSERT(1);

        // Actualizar la tabla y limpiar el campo
        actualizarTabla();
        txtNombrePlaylist.clear();
        mostrarAlerta("Éxito", "Playlist agregada correctamente.");
    }

    private void actualizarPlaylist() {
        PlaylistDAO seleccionada = tbvPlaylist.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Selecciona una playlist para actualizar.");
            return;
        }

        if (txtNombrePlaylist.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor, introduce un nuevo nombre para la playlist.");
            return;
        }

        // Actualizar la playlist seleccionada
        seleccionada.setNombrePlaylist(txtNombrePlaylist.getText());
        seleccionada.UPDATE();

        // Actualizar la tabla y limpiar el campo
        actualizarTabla();
        txtNombrePlaylist.clear();
        mostrarAlerta("Éxito", "Playlist actualizada correctamente.");
    }

    private void eliminarPlaylist() {
        PlaylistDAO seleccionada = tbvPlaylist.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Selecciona una playlist para eliminar.");
            return;
        }

        // Confirmar eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar esta playlist?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            seleccionada.DELETE();
            actualizarTabla();
            mostrarAlerta("Éxito", "Playlist eliminada correctamente.");
        }
    }

    private void actualizarTabla() {
        tbvPlaylist.setItems(new PlaylistDAO().SELECTALL());
        tbvPlaylist.refresh();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
