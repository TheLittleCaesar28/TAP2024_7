package com.example.tap2024_7.REPORTS;

import com.example.tap2024_7.MODELS.CancionDAO;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class OrdenComprarPDF {

    private Window stage;

    public void imprimirOrden(ObservableList<CancionDAO> carrito, double totalCompra) {
        // Crear un VBox para representar el contenido del PDF
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-font-family: Arial; -fx-font-size: 14px;");

        Label titulo = new Label("Orden de Compra");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

        Label totalLabel = new Label("Total: $" + String.format("%.2f", totalCompra));
        totalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black; -fx-font-weight: bold;");

        content.getChildren().add(titulo);

        // Agregar las canciones al contenido
        for (CancionDAO cancion : carrito) {
            Label cancionLabel = new Label(cancion.getNombreCancion() + " - $" + cancion.getPrecio());
            cancionLabel.setStyle("-fx-text-fill: black;");
            content.getChildren().add(cancionLabel);
        }

        content.getChildren().add(totalLabel);

        // Usar PrinterJob para imprimir
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean showDialog = job.showPrintDialog(stage); // Usar el Stage principal
            if (showDialog) {
                boolean success = job.printPage(content);
                if (success) {
                    job.endJob();
                    mostrarMensaje("Impresión Exitosa", "La orden de compra se imprimió correctamente.");
                } else {
                    mostrarMensaje("Error", "No se pudo completar la impresión.");
                }
            }
        } else {
            mostrarMensaje("Error", "No se pudo inicializar la impresión.");
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
