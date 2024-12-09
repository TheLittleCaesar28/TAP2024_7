package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.GeneroDAO;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FiltroGenero extends Stage {

    private ComboBox<GeneroDAO> cbGenero;

    public FiltroGenero() {
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles/filtroGenero.css").toExternalForm());

        this.setTitle("Filtros por Género");
        this.setScene(scene);
        this.show();
    }

    private VBox CrearContenido() {
        cbGenero = new ComboBox<>();
        cbGenero.setItems(new GeneroDAO().SELECTALL());
        cbGenero.setPromptText("Seleccione un Género");
        cbGenero.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(GeneroDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreGenero());
            }
        });
        cbGenero.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GeneroDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Seleccione un Género" : item.getNombreGenero());
            }
        });

        VBox vbox = new VBox(10, cbGenero);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }
}
