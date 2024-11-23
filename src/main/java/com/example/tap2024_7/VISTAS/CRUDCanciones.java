package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.AlbumDAO;
import com.example.tap2024_7.MODELS.CancionDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CRUDCanciones extends Stage {

    private TableView<CancionDAO> tbvCanciones;
    private TextField txtNombre, txtPrecio;
    private ComboBox<AlbumDAO> cbAlbumes;
    private Button btnAgregar, btnActualizar, btnEliminar;

    public CRUDCanciones() {
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 600, 700);
        // Añadir hoja de estilos
        scene.getStylesheets().add(getClass().getResource("/styles/crudCanciones.css").toExternalForm());

        this.setTitle("CRUD Canciones");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        // TableView setup
        tbvCanciones = new TableView<>();
        tbvCanciones.getStyleClass().add("table-view");

        TableColumn<CancionDAO, String> colNombre = new TableColumn<>("Canción");
        colNombre.setCellValueFactory(c -> c.getValue().nombreCancionProperty());

        TableColumn<CancionDAO, String> colAlbum = new TableColumn<>("Álbum");
        colAlbum.setCellValueFactory(c -> c.getValue().albumNombreProperty());

        TableColumn<CancionDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> c.getValue().precioProperty().asObject());

        tbvCanciones.getColumns().addAll(colNombre, colAlbum, colPrecio);
        tbvCanciones.setItems(new CancionDAO().SELECTALL());

        // TextFields
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la Canción");
        txtNombre.getStyleClass().add("text-field");

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio de la Canción");
        txtPrecio.getStyleClass().add("text-field");

        // ComboBox for Albums
        cbAlbumes = new ComboBox<>();
        cbAlbumes.setItems(new AlbumDAO().SELECTALL());
        cbAlbumes.setPromptText("Seleccione un Álbum");
        cbAlbumes.getStyleClass().add("combo-box");

        // Configuración de celdas para mostrar el nombre del álbum
        cbAlbumes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(AlbumDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreAlbum());
            }
        });
        CancionDAO cancion = new CancionDAO();
        cancion.setNombreCancion("Nueva Canción");
        cancion.setPrecio(19.99);
        cancion.setIdAlbum(1); // ID del álbum al que pertenece
        cancion.INSERT();


        cbAlbumes.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(AlbumDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione un Álbum" : item.getNombreAlbum());
            }
        });

        // Buttons
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarCancion());
        btnAgregar.getStyleClass().add("button");

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarCancion());
        btnActualizar.getStyleClass().add("button");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarCancion());
        btnEliminar.getStyleClass().add("button");
    }

    private VBox CrearContenido() {
        CrearUI();

        // HBox para los botones
        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        botones.getStyleClass().add("hbox");

        // VBox para el diseño general
        VBox vbox = new VBox(10, tbvCanciones, txtNombre, txtPrecio, cbAlbumes, botones);
        vbox.getStyleClass().add("vbox");

        return vbox;
    }

    private void agregarCancion() {
        if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || cbAlbumes.getSelectionModel().isEmpty()) {
            mostrarAlerta("Error", "Por favor, llena todos los campos obligatorios.");
            return;
        }

        CancionDAO cancion = new CancionDAO();
        cancion.setNombreCancion(txtNombre.getText());
        try {
            cancion.setPrecio(Double.parseDouble(txtPrecio.getText()));
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
            return;
        }

        cancion.setIdAlbum(cbAlbumes.getSelectionModel().getSelectedItem().getIdAlbum());
        cancion.INSERT();
        actualizarTabla();
    }

    private void actualizarCancion() {
        CancionDAO seleccionado = tbvCanciones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona una canción para actualizar.");
            return;
        }

        seleccionado.setNombreCancion(txtNombre.getText());
        try {
            seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
            return;
        }

        if (cbAlbumes.getSelectionModel().getSelectedItem() != null) {
            seleccionado.setIdAlbum(cbAlbumes.getSelectionModel().getSelectedItem().getIdAlbum());
        }

        seleccionado.UPDATE();
        actualizarTabla();
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

    private void actualizarTabla() {
        tbvCanciones.setItems(new CancionDAO().SELECTALL());
        tbvCanciones.refresh();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
