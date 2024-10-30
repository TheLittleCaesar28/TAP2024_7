package com.example.tap2024_7.VISTAS;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Buscaminas extends Stage {
    private GridPane gridPane;
    private TextField txtBombas;
    private Button btnCrear;
    private Label lblMensaje;
    private int filas = 10;
    private int columnas = 10;
    private List<Integer> posicionesBombas;
    private Button[][] botones;
    private boolean[][] marcadas;
    private boolean[][] descubiertas;
    private int casillasRestantes;

    public Buscaminas() {
        CrearUI();
        this.setTitle("Buscaminas");
        this.show();
    }

    private void CrearUI() {
        txtBombas = new TextField();
        txtBombas.setPromptText("Cantidad de bombas");

        btnCrear = new Button("Crear Campo");
        btnCrear.setOnAction(e -> crearCampo());

        lblMensaje = new Label();
        lblMensaje.getStyleClass().add("label-mensaje");

        VBox vbox = new VBox(10, txtBombas, btnCrear, lblMensaje);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("vbox-centrado");

        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));
        gridPane.getStyleClass().add("grid-buscaminas");

        vbox.getChildren().add(gridPane);
        Scene scene = new Scene(vbox, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/buscaminas.css").toExternalForm());
        this.setScene(scene);
    }

    private void crearCampo() {
        gridPane.getChildren().clear();
        int cantidadBombas;

        try {
            cantidadBombas = Integer.parseInt(txtBombas.getText());
        } catch (NumberFormatException e) {
            lblMensaje.setText("Por favor, introduce un número válido.");
            return;
        }

        if (cantidadBombas < 1 || cantidadBombas > (filas * columnas)) {
            lblMensaje.setText("La cantidad de bombas debe estar entre 1 y " + (filas * columnas));
            return;
        }

        posicionesBombas = new ArrayList<>();
        while (posicionesBombas.size() < cantidadBombas) {
            int posicion = (int) (Math.random() * (filas * columnas));
            if (!posicionesBombas.contains(posicion)) {
                posicionesBombas.add(posicion);
            }
        }

        botones = new Button[filas][columnas];
        marcadas = new boolean[filas][columnas];
        descubiertas = new boolean[filas][columnas];
        casillasRestantes = filas * columnas - cantidadBombas;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button btn = new Button();
                btn.setPrefSize(30, 30);
                int finalI = i;
                int finalJ = j;

                btn.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        manejarClickIzquierdo(finalI, finalJ);
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        manejarClickDerecho(btn, finalI, finalJ);
                    }
                });

                botones[i][j] = btn;
                gridPane.add(btn, j, i);
            }
        }

        lblMensaje.setText("Campo creado. ¡Buena suerte!");
    }

    private void manejarClickIzquierdo(int fila, int columna) {
        if (marcadas[fila][columna] || descubiertas[fila][columna]) return;

        int posicion = fila * columnas + columna;
        if (posicionesBombas.contains(posicion)) {
            lblMensaje.setText("¡Has perdido! Hay una bomba en esta casilla.");
            revelarBombas();
        } else {
            int cantidadBombasAdyacentes = contarBombasAdyacentes(fila, columna);
            botones[fila][columna].setText(String.valueOf(cantidadBombasAdyacentes));
            botones[fila][columna].setDisable(true);
            botones[fila][columna].getStyleClass().add("boton-descubierto");
            descubiertas[fila][columna] = true;
            casillasRestantes--;

            if (cantidadBombasAdyacentes == 0) {
                mostrarCasillasVacias(fila, columna);
            }

            if (casillasRestantes == 0) {
                lblMensaje.setText("¡Has ganado! Felicidades.");
            }
        }
    }

    private void manejarClickDerecho(Button btn, int fila, int columna) {
        if (descubiertas[fila][columna]) return;

        if (marcadas[fila][columna]) {
            btn.setStyle("");
            marcadas[fila][columna] = false;
        } else {
            btn.setStyle("-fx-background-image: url('/images/bandera.png'); -fx-background-size: 25px 25px;");
            marcadas[fila][columna] = true;
        }
    }

    private void mostrarCasillasVacias(int fila, int columna) {
        for (int i = Math.max(0, fila - 1); i <= Math.min(filas - 1, fila + 1); i++) {
            for (int j = Math.max(0, columna - 1); j <= Math.min(columnas - 1, columna + 1); j++) {
                if (!descubiertas[i][j]) {
                    manejarClickIzquierdo(i, j);
                }
            }
        }
    }

    private int contarBombasAdyacentes(int fila, int columna) {
        int contador = 0;
        for (int i = Math.max(0, fila - 1); i <= Math.min(filas - 1, fila + 1); i++) {
            for (int j = Math.max(0, columna - 1); j <= Math.min(columnas - 1, columna + 1); j++) {
                if (posicionesBombas.contains(i * columnas + j)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    private void revelarBombas() {
        for (Integer bomba : posicionesBombas) {
            int f = bomba / columnas;
            int c = bomba % columnas;
            botones[f][c].setStyle("-fx-background-color: red;");
        }
    }
}
