package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.CancionDAO;
import com.example.tap2024_7.MODELS.CompraDAO;
import com.example.tap2024_7.REPORTS.OrdenComprarPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Comprar extends Stage {

    private TableView<CancionDAO> tbvCanciones;
    private ObservableList<CancionDAO> carrito = FXCollections.observableArrayList();
    private Label lblTotal;
    private double totalCompra = 0.0;

    private int idUsuario;

    private static final String BASE_PATH = "C:\\Users\\eduar\\OneDrive\\Escritorio\\ITC\\7mo Semestre\\Tópicos Avanzados de Programación\\Albumes\\";

    public Comprar(int idUsuario) {
        this.idUsuario = idUsuario;
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 800, 700);

        var cssResource = getClass().getResource("/styles/comprar.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        } else {
            System.err.println("Archivo CSS no encontrado.");
        }

        this.setTitle("Comprar Canciones/Álbumes");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        tbvCanciones = new TableView<>();

        TableColumn<CancionDAO, Void> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CancionDAO cancion = getTableView().getItems().get(getIndex());
                    String rutaImagenRelativa = cancion.getAlbumImagenRuta();
                    if (rutaImagenRelativa != null && !rutaImagenRelativa.isEmpty()) {
                        try {
                            String rutaCompleta = BASE_PATH + rutaImagenRelativa;
                            File archivoImagen = new File(rutaCompleta);
                            if (archivoImagen.exists() && archivoImagen.isFile()) {
                                imageView.setImage(new Image(archivoImagen.toURI().toString(), 50, 50, true, true));
                            } else {
                                System.err.println("La imagen no se encontró en la ruta: " + rutaCompleta);
                                imageView.setImage(null);
                            }
                        } catch (Exception e) {
                            System.err.println("Error cargando la imagen: " + e.getMessage());
                            imageView.setImage(null);
                        }
                    } else {
                        imageView.setImage(null);
                    }
                    setGraphic(imageView);
                }
            }
        });

        TableColumn<CancionDAO, String> colNombre = new TableColumn<>("Canción");
        colNombre.setCellValueFactory(c -> c.getValue().nombreCancionProperty());

        TableColumn<CancionDAO, String> colAlbum = new TableColumn<>("Álbum");
        colAlbum.setCellValueFactory(c -> c.getValue().albumNombreProperty());

        TableColumn<CancionDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> c.getValue().precioProperty().asObject());

        TableColumn<CancionDAO, Void> colAgregar = new TableColumn<>("Agregar");
        colAgregar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Agregar");

            {
                btn.getStyleClass().add("button");
                btn.setOnAction(event -> agregarCancion(getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        tbvCanciones.getColumns().addAll(colImagen, colNombre, colAlbum, colPrecio, colAgregar);

        ObservableList<CancionDAO> canciones = new CancionDAO().SELECTALL();
        if (canciones.isEmpty()) {
            System.err.println("No se pudieron cargar las canciones.");
        }
        tbvCanciones.setItems(canciones);
        tbvCanciones.getStyleClass().add("table-view");

        lblTotal = new Label("Total: $0.00");
        lblTotal.getStyleClass().add("label-mensaje");
    }

    private VBox CrearContenido() {
        CrearUI();

        Button btnPagar = new Button("Pagar");
        btnPagar.setOnAction(event -> procesarCompra());
        btnPagar.getStyleClass().add("button");

        Button btnImprimirPDF = new Button("Imprimir PDF");
        btnImprimirPDF.setOnAction(event -> {
            OrdenComprarPDF reporte = new OrdenComprarPDF();
            reporte.imprimirOrden(carrito, totalCompra); // Pasar el Stage actual
        });

        btnImprimirPDF.getStyleClass().add("button");

        HBox botones = new HBox(20, btnPagar, btnImprimirPDF);
        botones.getStyleClass().add("hbox");

        VBox vbox = new VBox(20, tbvCanciones, lblTotal, botones);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }

    private void agregarCancion(int index) {
        CancionDAO seleccionada = tbvCanciones.getItems().get(index);
        carrito.add(seleccionada);
        totalCompra += seleccionada.getPrecio();
        lblTotal.setText("Total: $" + String.format("%.2f", totalCompra));
    }

    private void procesarCompra() {
        CompraDAO compra = new CompraDAO();
        int idCompra = compra.INSERT(idUsuario);

        for (CancionDAO cancion : carrito) {
            compra.insertDetalle(idCompra, cancion.getIdCancion());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Compra Realizada");
        alert.setContentText("¡Tu compra ha sido procesada correctamente!");
        alert.showAndWait();

    }

}
