package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.CompraDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HistorialCompras extends Stage {

    private TableView<CompraDAO> tbvHistorial;


    public HistorialCompras(int idUsuario) {

        VBox contenido = CrearContenido(idUsuario);
        Scene scene = new Scene(contenido, 800, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/historialcompras.css").toExternalForm());
        this.setTitle("Historial de Compras");
        this.setScene(scene);
        this.show();
    }

    private VBox CrearContenido(int idUsuario) {
        CrearUI(idUsuario);

        VBox vbox = new VBox(tbvHistorial);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }

    private void CrearUI(int idUsuario) {
        // Inicializar TableView
        tbvHistorial = new TableView<>();
        tbvHistorial.getStyleClass().add("table-view");

        // Configurar columnas
        TableColumn<CompraDAO, Integer> colIdCompra = new TableColumn<>("ID Compra");
        colIdCompra.setCellValueFactory(new PropertyValueFactory<>("idCompra"));
        colIdCompra.setPrefWidth(100);

        TableColumn<CompraDAO, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        colFecha.setPrefWidth(200);

        TableColumn<CompraDAO, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));
        colTotal.setPrefWidth(100);

        TableColumn<CompraDAO, String> colCanciones = new TableColumn<>("Canciones Compradas");
        colCanciones.setCellValueFactory(new PropertyValueFactory<>("cancionesCompradasTexto")); // Mostrar como texto
        colCanciones.setPrefWidth(400);

        // Personalizar celdas para mostrar el texto completo con salto de línea
        colCanciones.setCellFactory(tc -> new TableCell<>() {
            private final Text text = new Text();

            {
                text.getStyleClass().add("text"); // Estilo definido en CSS
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });

        // Centrar las demás columnas
        centrarColumna(colIdCompra);
        centrarColumna(colFecha);
        centrarColumna(colTotal);

        // Agregar columnas al TableView
        tbvHistorial.getColumns().addAll(colIdCompra, colFecha, colTotal, colCanciones);

        // Cargar datos en la tabla
        CompraDAO compraDAO = new CompraDAO();
        tbvHistorial.setItems(compraDAO.SELECTALL(idUsuario));

        // Evitar mostrar filas vacías
        Text emptyText = new Text("No hay compras registradas.");
        emptyText.getStyleClass().add("text");
        tbvHistorial.setPlaceholder(emptyText);

        tbvHistorial.setRowFactory(tv -> {
            TableRow<CompraDAO> row = new TableRow<>();
            row.setStyle("-fx-padding: 5;"); // Espaciado interno en las filas
            row.setPrefHeight(40); // Altura de fila ajustada
            return row;
        });
    }

    // Método para centrar las columnas
    private <T> void centrarColumna(TableColumn<CompraDAO, T> column) {
        column.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
                setAlignment(Pos.CENTER); // Centrar el contenido
            }
        });
    }
}
