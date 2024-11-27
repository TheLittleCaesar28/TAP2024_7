package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.CompraDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class Estadisticas extends Stage {

    public Estadisticas() {
        VBox mainContainer = new VBox(20);
        mainContainer.setStyle("-fx-alignment: center; -fx-background-color: #191414; -fx-padding: 20;");

        Label titulo = new Label("Estadísticas de Ventas por Mes");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1DB954;");

        HBox chartsContainer = new HBox(20);
        chartsContainer.setAlignment(Pos.CENTER);
        chartsContainer.setPadding(new Insets(20));

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Distribución de Ventas");
        pieChart.setStyle("-fx-background-color: transparent;");

        BarChart<String, Number> barChart = createBarChart();

        CompraDAO compraDAO = new CompraDAO();
        Map<String, Integer> ventasPorMes = compraDAO.obtenerVentasPorMes();

        if (ventasPorMes.isEmpty()) {
            Label sinDatos = new Label("No hay datos disponibles para mostrar.");
            sinDatos.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
            mainContainer.getChildren().addAll(titulo, sinDatos);
        } else {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            ventasPorMes.forEach((mes, cantidad) -> {
                PieChart.Data pieData = new PieChart.Data(mes, cantidad);
                pieChart.getData().add(pieData);

                XYChart.Data<String, Number> barData = new XYChart.Data<>(mes, cantidad);
                series.getData().add(barData);
            });
            barChart.getData().add(series);

            TableView<Map.Entry<String, Integer>> tableView = createDataTable(ventasPorMes);

            chartsContainer.getChildren().addAll(pieChart, barChart);
            mainContainer.getChildren().addAll(titulo, chartsContainer, tableView);
        }

        Scene scene = new Scene(mainContainer, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/estadisticas.css").toExternalForm());

        this.setTitle("Estadísticas de Ventas");
        this.setScene(scene);
        this.show();
    }

    private BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Mes");
        xAxis.setStyle("-fx-tick-label-fill: black; -fx-font-size: 14px;");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas");
        yAxis.setStyle("-fx-tick-label-fill: black; -fx-font-size: 14px;");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Ventas por Mes");
        barChart.setStyle("-fx-background-color: transparent;");
        barChart.setLegendVisible(false);

        return barChart;
    }

    private TableView<Map.Entry<String, Integer>> createDataTable(Map<String, Integer> ventasPorMes) {
        TableView<Map.Entry<String, Integer>> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: white;");

        TableColumn<Map.Entry<String, Integer>, String> colMes = new TableColumn<>("Mes");
        colMes.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));
        colMes.setPrefWidth(200);

        TableColumn<Map.Entry<String, Integer>, Integer> colVentas = new TableColumn<>("Ventas");
        colVentas.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getValue()));
        colVentas.setPrefWidth(100);

        tableView.getColumns().addAll(colMes, colVentas);
        tableView.getItems().addAll(ventasPorMes.entrySet());

        return tableView;
    }
}
