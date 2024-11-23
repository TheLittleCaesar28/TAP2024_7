package com.example.tap2024_7.REPORTS;

import com.example.tap2024_7.MODELS.CancionDAO;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.util.List;

public class OrdenComprarPDF {

    public void imprimirOrden(List<CancionDAO> carrito, double totalCompra) {
        // Crear un VBox que representa el contenido a imprimir
        VBox contenidoImpresion = new VBox();

        // Título
        javafx.scene.control.Label titulo = new javafx.scene.control.Label("Orden de Compra");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-alignment: center;");
        contenidoImpresion.getChildren().add(titulo);

        // Espaciado
        contenidoImpresion.getChildren().add(new javafx.scene.control.Label("\n"));

        // Tabla de contenido
        javafx.scene.control.TableView<CancionDAO> tabla = new javafx.scene.control.TableView<>();
        javafx.scene.control.TableColumn<CancionDAO, String> colCancion = new javafx.scene.control.TableColumn<>("Canción");
        colCancion.setCellValueFactory(c -> c.getValue().nombreCancionProperty());

        javafx.scene.control.TableColumn<CancionDAO, String> colAlbum = new javafx.scene.control.TableColumn<>("Álbum");
        colAlbum.setCellValueFactory(c -> c.getValue().albumNombreProperty());

        javafx.scene.control.TableColumn<CancionDAO, String> colPrecio = new javafx.scene.control.TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                "$" + String.format("%.2f", c.getValue().getPrecio())));

        tabla.getColumns().addAll(colCancion, colAlbum, colPrecio);
        tabla.getItems().addAll(carrito);

        contenidoImpresion.getChildren().add(tabla);

        // Total de la compra
        javafx.scene.control.Label totalLabel = new javafx.scene.control.Label("Total: $" + String.format("%.2f", totalCompra));
        totalLabel.setStyle("-fx-font-size: 14px; -fx-alignment: right;");
        contenidoImpresion.getChildren().add(totalLabel);

        // Crear un trabajo de impresión
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null && printerJob.showPrintDialog(null)) {
            Scene escena = new Scene(contenidoImpresion); // Crear una escena con el contenido
            boolean success = printerJob.printPage(escena.getRoot()); // Imprimir la raíz de la escena

            if (success) {
                printerJob.endJob();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Generado");
                alert.setContentText("¡El PDF ha sido generado correctamente!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Hubo un problema al generar el PDF.");
                alert.showAndWait();
            }
        }
    }
}
