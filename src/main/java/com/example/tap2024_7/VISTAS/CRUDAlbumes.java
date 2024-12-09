package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.AlbumDAO;
import com.example.tap2024_7.MODELS.ArtistaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CRUDAlbumes extends Stage {

    private TableView<AlbumDAO> tbvAlbumes;
    private TextField txtNombre;
    private ComboBox<ArtistaDAO> cbArtistas;
    private Button btnAgregar, btnActualizar, btnEliminar, btnCargarImagen;
    private ImageView imgAlbum;
    private File archivoImagen;

    public CRUDAlbumes() {
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 600, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/crudAlbumes.css").toExternalForm());

        this.setTitle("CRUD Álbumes");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        // Tabla
        tbvAlbumes = new TableView<>();
        tbvAlbumes.getStyleClass().add("table-view");

        TableColumn<AlbumDAO, String> colNombre = new TableColumn<>("Nombre del Álbum");
        colNombre.setCellValueFactory(c -> c.getValue().nombreAlbumProperty());

        TableColumn<AlbumDAO, String> colArtista = new TableColumn<>("Artista");
        colArtista.setCellValueFactory(c -> c.getValue().nombreArtistaProperty());

        TableColumn<AlbumDAO, Void> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AlbumDAO album = getTableView().getItems().get(getIndex());
                    String nombreImagen = album.getRutaImagen(); // Solo nombre del archivo
                    if (nombreImagen != null && !nombreImagen.isEmpty()) {
                        File archivoImagen = new File("C:\\Users\\eduar\\OneDrive\\Escritorio\\ITC\\7mo Semestre\\Tópicos Avanzados de Programación\\Albumes\\" + nombreImagen); // Ruta relativa al proyecto
                        if (archivoImagen.exists()) {
                            imageView.setImage(new Image(archivoImagen.toURI().toString(), 50, 50, true, true));
                        } else {
                            imageView.setImage(new Image("file:imagenes/placeholder.jpg", 50, 50, true, true)); // Imagen por defecto
                        }
                    }
                    setGraphic(imageView);
                }
            }
        });

        tbvAlbumes.getColumns().addAll(colNombre, colArtista, colImagen);
        tbvAlbumes.setItems(new AlbumDAO().SELECTALL());

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Álbum");

        cbArtistas = new ComboBox<>();
        cbArtistas.setPromptText("Selecciona un Artista");
        cbArtistas.setItems(new ArtistaDAO().SELECTALL());
        cbArtistas.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ArtistaDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreArtista());
            }
        });
        cbArtistas.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ArtistaDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Selecciona un artista" : item.getNombreArtista());
            }
        });

        imgAlbum = new ImageView();
        imgAlbum.setFitHeight(150);
        imgAlbum.setFitWidth(150);
        imgAlbum.getStyleClass().add("image-view");

        btnCargarImagen = new Button("Cargar Imagen");
        btnCargarImagen.setOnAction(e -> cargarImagen());
        btnCargarImagen.getStyleClass().add("button");

        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarAlbum());
        btnAgregar.getStyleClass().add("button");

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarAlbum());
        btnActualizar.getStyleClass().add("button");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarAlbum());
        btnEliminar.getStyleClass().add("button");
    }

    private VBox CrearContenido() {
        CrearUI();
        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        VBox vbox = new VBox(10, tbvAlbumes, txtNombre, cbArtistas, imgAlbum, btnCargarImagen, botones);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }

    private void cargarImagen() {
        FileChooser fileChooser = new FileChooser();
        archivoImagen = fileChooser.showOpenDialog(this);
        if (archivoImagen != null) {
            Image image = new Image(archivoImagen.toURI().toString());
            imgAlbum.setImage(image);
        }
    }

    private void agregarAlbum() {
        if (txtNombre.getText().isEmpty() || cbArtistas.getSelectionModel().isEmpty()) {
            mostrarAlerta("Error", "Campos obligatorios vacíos", "Por favor, llena todos los campos obligatorios.");
            return;
        }

        AlbumDAO album = new AlbumDAO();
        album.setNombreAlbum(txtNombre.getText());
        ArtistaDAO artistaSeleccionado = cbArtistas.getSelectionModel().getSelectedItem();
        if (artistaSeleccionado != null) {
            album.setIdArtista(artistaSeleccionado.getIdArtista());
        }

        if (archivoImagen != null) {
            String nombreImagen = archivoImagen.getName();
            album.setRutaImagen(nombreImagen);

            File destino = new File("imagenes/" + nombreImagen);
            if (!destino.exists()) {
                try {
                    Files.copy(archivoImagen.toPath(), destino.toPath());
                } catch (IOException e) {
                    mostrarAlerta("Error", "No se pudo mover la imagen", e.getMessage());
                }
            }
        }
        album.INSERT();
        actualizarTabla();
    }

    private void actualizarAlbum() {
        AlbumDAO seleccionado = tbvAlbumes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombreAlbum(txtNombre.getText());
            ArtistaDAO artistaSeleccionado = cbArtistas.getSelectionModel().getSelectedItem();
            if (artistaSeleccionado != null) {
                seleccionado.setIdArtista(artistaSeleccionado.getIdArtista());
            }
            seleccionado.UPDATE();
            actualizarTabla();
        } else {
            mostrarAlerta("Error", "Selección inválida", "Selecciona un álbum para actualizar.");
        }
    }

    private void eliminarAlbum() {
        AlbumDAO seleccionado = tbvAlbumes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.DELETE();
            actualizarTabla();
        } else {
            mostrarAlerta("Error", "Selección inválida", "Selecciona un álbum para eliminar.");
        }
    }

    private void actualizarTabla() {
        tbvAlbumes.setItems(new AlbumDAO().SELECTALL());
        tbvAlbumes.refresh();
    }

    private void mostrarAlerta(String titulo, String cabecera, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
