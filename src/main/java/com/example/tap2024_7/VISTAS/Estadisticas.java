package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.CompraDAO;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class Estadisticas extends Stage {

    public Estadisticas() {
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");

        Label titulo = new Label("Estadísticas de Ventas por Mes");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1DB954;"); /* Bootstrap blue */

        PieChart pieChart = new PieChart();
        pieChart.getStyleClass().add("chart-pie");

        // Obtener estadísticas
        CompraDAO compraDAO = new CompraDAO();
        Map<String, Integer> ventasPorMes = compraDAO.obtenerVentasPorMes();

        // Agregar datos al gráfico
        ventasPorMes.forEach((mes, cantidad) -> {
            PieChart.Data data = new PieChart.Data(mes, cantidad);
            pieChart.getData().add(data);
        });

        vbox.getChildren().addAll(titulo, pieChart);

        Scene scene = new Scene(vbox, 600, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/estadisticas.css").toExternalForm());

        this.setTitle("Estadísticas de Ventas");
        this.setScene(scene);
        this.show();
    }
}
