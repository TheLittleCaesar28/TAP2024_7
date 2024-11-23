package com.example.tap2024_7.REPORTS;

import com.example.tap2024_7.MODELS.CancionDAO;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.List;

public class ReporteArtistasCanciones {

    public void imprimirReporte(List<CancionDAO> canciones) {
        // Crear un VBox para contener el contenido del informe
        VBox contenidoImpresion = new VBox();

        // Título del informe
        Label titulo = new Label("Reporte de Artistas y Canciones");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-alignment: center;");
        contenidoImpresion.getChildren().add(titulo);

        // Tabla para mostrar las canciones
        TableView<CancionDAO> tabla = new TableView<>();

        // Columna: Canción
        TableColumn<CancionDAO, String> colCancion = new TableColumn<>("Canción");
        colCancion.setCellValueFactory(c -> c.getValue().nombreCancionProperty());

        // Columna: Álbum
        TableColumn<CancionDAO, String> colAlbum = new TableColumn<>("Álbum");
        colAlbum.setCellValueFactory(c -> c.getValue().albumNombreProperty());

        // Columna: Precio
        TableColumn<CancionDAO, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                "$" + String.format("%.2f", c.getValue().getPrecio())));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colCancion, colAlbum, colPrecio);
        tabla.getItems().addAll(canciones);

        // Agregar la tabla al contenido
        contenidoImpresion.getChildren().add(tabla);

        // Crear un trabajo de impresión
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            // Crear una escena temporal con el contenido del informe
            Scene escena = new Scene(contenidoImpresion);

            // Imprimir la raíz de la escena
            boolean success = printerJob.printPage(escena.getRoot());

            if (success) {
                printerJob.endJob();
                System.out.println("¡Reporte impreso correctamente!");
            } else {
                System.out.println("Error al imprimir el reporte.");
            }
        } else {
            System.out.println("El trabajo de impresión no se inició.");
        }
    }

    public void generarReporte(ObservableList<CancionDAO> selectall) {
    }
}
