package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CRUDCanciones extends Stage {

    private TableView<CancionDAO> tbvCanciones;
    private TextField txtNombre, txtPrecio;
    private ComboBox<AlbumDAO> cbAlbumes;
    private ComboBox<IdiomaDAO> cbIdiomas;
    private ComboBox<GeneroDAO> cbGenero;
    private ComboBox<PlaylistDAO> cbPlaylist;
    private Button btnAgregar, btnActualizar, btnEliminar, btnFiltrar, btnAgregarAPlaylist;
    private ComboBox<IdiomaDAO> cbFiltroIdioma;
    private ComboBox<GeneroDAO> cbFiltroGenero;

    public CRUDCanciones() {
        VBox contenido = crearContenido();
        Scene scene = new Scene(contenido, 800, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/crudCanciones.css").toExternalForm());

        this.setTitle("CRUD Canciones");
        this.setScene(scene);
        this.show();
    }

    private void crearUI() {
        // Crear tabla
        tbvCanciones = new TableView<>();
        tbvCanciones.getStyleClass().add("table-view");

        TableColumn<CancionDAO, String> colNombre = new TableColumn<>("Canción");
        colNombre.setCellValueFactory(c -> c.getValue().nombreCancionProperty());

        TableColumn<CancionDAO, String> colAlbum = new TableColumn<>("Álbum");
        colAlbum.setCellValueFactory(c -> c.getValue().albumNombreProperty());

        TableColumn<CancionDAO, String> colIdioma = new TableColumn<>("Idioma");
        colIdioma.setCellValueFactory(c -> c.getValue().idiomaNombreProperty());

        TableColumn<CancionDAO, String> colGenero = new TableColumn<>("Género");
        colGenero.setCellValueFactory(c -> c.getValue().generoNombreProperty());

        TableColumn<CancionDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> c.getValue().precioProperty().asObject());

        tbvCanciones.getColumns().addAll(colNombre, colAlbum, colIdioma, colGenero, colPrecio);
        tbvCanciones.setItems(new CancionDAO().SELECTALL());

        // Campos de entrada
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la Canción");

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio de la Canción");

        // ComboBox de selección
        cbAlbumes = new ComboBox<>();
        cbAlbumes.setItems(new AlbumDAO().SELECTALL());
        cbAlbumes.setPromptText("Seleccione un Álbum");

        cbIdiomas = new ComboBox<>();
        cbIdiomas.setItems(new IdiomaDAO().SELECTALL());
        cbIdiomas.setPromptText("Seleccione un Idioma");

        cbGenero = new ComboBox<>();
        cbGenero.setItems(new GeneroDAO().SELECTALL());
        cbGenero.setPromptText("Seleccione un Género");

        cbPlaylist = new ComboBox<>();
        cbPlaylist.setItems(new PlaylistDAO().SELECTALL());
        cbPlaylist.setPromptText("Seleccione una Playlist");

        // Botones
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarCancion());

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarCancion());

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarCancion());

        btnFiltrar = new Button("Filtrar");
        btnFiltrar.setOnAction(e -> actualizarTabla());

        btnAgregarAPlaylist = new Button("Agregar a Playlist");
        btnAgregarAPlaylist.setOnAction(e -> agregarCancionAPlaylist());
    }

    private VBox crearContenido() {
        crearUI();

        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar, btnAgregarAPlaylist);

        VBox vbox = new VBox(10, tbvCanciones, txtNombre, txtPrecio, cbAlbumes, cbIdiomas, cbGenero, cbPlaylist, botones);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }

    private void agregarCancion() {
        if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || cbAlbumes.getSelectionModel().isEmpty() || cbIdiomas.getSelectionModel().isEmpty() || cbGenero.getSelectionModel().isEmpty()) {
            mostrarAlerta("Error", "Por favor, llena todos los campos obligatorios.");
            return;
        }

        CancionDAO cancion = new CancionDAO();
        try {
            cancion.setNombreCancion(txtNombre.getText());
            cancion.setPrecio(Double.parseDouble(txtPrecio.getText()));
            cancion.setIdAlbum(cbAlbumes.getSelectionModel().getSelectedItem().getIdAlbum());
            cancion.setIdIdioma(cbIdiomas.getSelectionModel().getSelectedItem().getIdIdioma());
            cancion.setIdGenero(cbGenero.getSelectionModel().getSelectedItem().getIdGenero());
            cancion.INSERT();
            actualizarTabla();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
        }
    }

    private void agregarCancionAPlaylist() {
        CancionDAO seleccionada = tbvCanciones.getSelectionModel().getSelectedItem();
        PlaylistDAO playlistSeleccionada = cbPlaylist.getSelectionModel().getSelectedItem();

        if (seleccionada == null || playlistSeleccionada == null) {
            mostrarAlerta("Error", "Selecciona una canción y una playlist.");
            return;
        }

        playlistSeleccionada.agregarCancionAPlaylist(seleccionada.getIdCancion());
        mostrarAlerta("Éxito", "Canción agregada a la playlist.");
    }

    private void actualizarCancion() {
        CancionDAO seleccionado = tbvCanciones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona una canción para actualizar.");
            return;
        }

        try {
            seleccionado.setNombreCancion(txtNombre.getText());
            seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
            if (cbAlbumes.getSelectionModel().getSelectedItem() != null) {
                seleccionado.setIdAlbum(cbAlbumes.getSelectionModel().getSelectedItem().getIdAlbum());
            }
            if (cbIdiomas.getSelectionModel().getSelectedItem() != null) {
                seleccionado.setIdIdioma(cbIdiomas.getSelectionModel().getSelectedItem().getIdIdioma());
            }
            if (cbGenero.getSelectionModel().getSelectedItem() != null) {
                seleccionado.setIdGenero(cbGenero.getSelectionModel().getSelectedItem().getIdGenero());
            }
            seleccionado.UPDATE();
            actualizarTabla();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
        }
    }

    private void actualizarTabla() {
        String filtroIdioma = cbFiltroIdioma.getSelectionModel().isEmpty() ? null : cbFiltroIdioma.getSelectionModel().getSelectedItem().getNombreIdioma();
        String filtroGenero = cbFiltroGenero.getSelectionModel().isEmpty() ? null : cbFiltroGenero.getSelectionModel().getSelectedItem().getNombreGenero();
        tbvCanciones.setItems(new CancionDAO().SELECTALL(filtroIdioma, filtroGenero));
        tbvCanciones.refresh();
    }

    private void eliminarCancion() {
        CancionDAO seleccionado = tbvCanciones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona una canción para eliminar.");
            return;
        }
        seleccionado.DELETE();
        actualizarTabla();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
