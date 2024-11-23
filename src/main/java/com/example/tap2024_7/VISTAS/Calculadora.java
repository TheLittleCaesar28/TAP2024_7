package com.example.tap2024_7.VISTAS;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage{
    private Button[][] arBtns;
    private Button btnClear;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    private String operacionActual = "";
    private String operando1 = "";
    private String operando2 = "";
    private boolean isSecondOperand = false;

    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-"};

    private void CrearUI() {
        arBtns = new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);

        gdpTeclado = new GridPane();
        CrearTeclado();

        btnClear = new Button("Clear");
        btnClear.setOnAction(event -> limpiarPantalla());

        vBox = new VBox(txtPantalla, gdpTeclado, btnClear);
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/cal.css").toString());
    }

    private void CrearTeclado() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBtns[i][j] = new Button(strTeclas[4 * i + j]);
                arBtns[i][j].setPrefSize(50, 50);
                int finalI = i;
                int finalJ = j;
                arBtns[i][j].setOnAction(event -> detectarTecla(strTeclas[4 * finalI + finalJ]));
                gdpTeclado.add(arBtns[i][j], j, i);
            }
        }
    }

    public Calculadora() {
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void detectarTecla(String tecla) {

        switch (tecla) {
            case "+":
            case "-":
            case "*":
            case "/":
                if (!isSecondOperand && !operando1.isEmpty()) {
                    operacionActual = tecla;
                    isSecondOperand = true;
                } else {
                    mostrarError("Operación inválida");
                }
                break;
            case "=":
                if (isSecondOperand && !operando2.isEmpty()) {
                    realizarOperacion();
                } else {
                    mostrarError("Faltan datos");
                }
                break;
            case ".":
                if (isSecondOperand) {
                    if (!operando2.contains(".")) operando2 += ".";
                } else {
                    if (!operando1.contains(".")) operando1 += ".";
                }
                actualizarPantalla();
                break;
            default:
                if (Character.isDigit(tecla.charAt(0))) {
                    if (isSecondOperand) {
                        operando2 += tecla;
                    } else {
                        operando1 += tecla;
                    }
                    actualizarPantalla();
                }
                break;
        }
    }

    private void realizarOperacion() {

        if (operando1.isEmpty() || operando2.isEmpty()) {
            mostrarError("Faltan operandos");
            return;
        }


        double resultado = 0;
        double num1;
        double num2;

        try {
            num1 = Double.parseDouble(operando1);
            num2 = Double.parseDouble(operando2);
        } catch (NumberFormatException e) {
            mostrarError("Error en formato numérico");
            return;
        }


        switch (operacionActual) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    mostrarError("División por cero");
                    return;
                }
                break;
            default:
                mostrarError("Operación no válida");
                return;
        }


        txtPantalla.setText(String.valueOf(resultado));
        reiniciar();
    }

    private void actualizarPantalla() {
        if (isSecondOperand) {
            txtPantalla.setText(operando2);
        } else {
            txtPantalla.setText(operando1);
        }
    }

    private void limpiarPantalla() {
        txtPantalla.setText("0");
        reiniciar();
    }

    private void reiniciar() {
        operando1 = "";
        operando2 = "";
        operacionActual = "";
        isSecondOperand = false;
    }

    private void mostrarError(String mensaje) {
        txtPantalla.setText(mensaje);
        reiniciar();
    }
}
