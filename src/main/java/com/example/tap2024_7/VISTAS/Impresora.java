package com.example.tap2024_7.VISTAS;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Impresora extends Stage {
    private ObservableList<Tarea> listaTareas = FXCollections.observableArrayList();
    private Random generador = new Random();

    private boolean encendida = true;
    private Button botonAgregar;
    private Button botonEncender;
    private ProgressBar barraProgreso;
    private TableView<Tarea> tablaTareas;
    private Timeline progresoTarea;

    private double progresoGuardado = 0; // Guarda el progreso actual

    public Impresora() {
        inicializarComponentes();
        configurarEventos();
        configurarVentana();
        iniciarHiloMonitoreo();
    }

    private void inicializarComponentes() {
        botonAgregar = new Button("Agregar Tarea");
        botonEncender = new Button("Apagar");
        barraProgreso = new ProgressBar(0);
        tablaTareas = crearTabla();
    }

    private void configurarVentana() {
        VBox layoutPrincipal = new VBox(10, crearBotones(), barraProgreso, tablaTareas);
        layoutPrincipal.setPadding(new Insets(20));
        Scene escena = new Scene(layoutPrincipal, 800, 600);
        this.setTitle("Impresora");
        this.setScene(escena);
        this.show();
    }

    private HBox crearBotones() {
        HBox botones = new HBox(20, botonAgregar, botonEncender);
        botones.setPadding(new Insets(10));
        return botones;
    }

    private TableView<Tarea> crearTabla() {
        TableView<Tarea> tabla = new TableView<>();
        tabla.getColumns().addAll(
                crearColumna("Numero del Archivo", "noArchivo", Integer.class),
                crearColumna("Nombre del Archivo", "nombreArchivo", String.class),
                crearColumna("Numero de Hojas", "numHojas", Integer.class),
                crearColumna("Fecha", "fecha", LocalDate.class),
                crearColumna("Hora", "hora", LocalTime.class)
        );
        tabla.setItems(listaTareas);
        return tabla;
    }

    private <T> TableColumn<Tarea, T> crearColumna(String titulo, String propiedad, Class<T> tipo) {
        TableColumn<Tarea, T> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        return columna;
    }

    private void configurarEventos() {
        botonAgregar.setOnAction(e -> agregarTarea());
        botonEncender.setOnAction(e -> cambiarEstadoImpresora());
    }

    private void agregarTarea() {
        int noArchivo = generador.nextInt(100) + 1;
        int numHojas = generador.nextInt(21) + 10;
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        String nombreArchivo = fecha + "_" + hora.toString().replace(":", "-") + ".pdf";

        listaTareas.add(new Tarea(noArchivo, nombreArchivo, numHojas, fecha, hora));

        if (listaTareas.size() == 1 && encendida) {
            iniciarBarraProgreso();
        }
    }

    private void cambiarEstadoImpresora() {
        encendida = !encendida;
        botonEncender.setText(encendida ? "Apagar" : "Prender");

        if (encendida) {
            // Continúa desde el progreso guardado
            iniciarBarraProgreso();
        } else if (progresoTarea != null) {
            // Detiene el progreso y guarda el estado actual
            progresoTarea.stop();
            progresoGuardado = barraProgreso.getProgress();
        }
    }

    private void iniciarBarraProgreso() {
        if (!encendida || listaTareas.isEmpty()) return;

        int duracionSegundos = generador.nextInt(6) + 5; // Random entre 5 y 10 segundos
        double incremento = 1.0 / (duracionSegundos * 10); // Incremento en pasos de 100ms
        barraProgreso.setProgress(progresoGuardado); // Retoma el progreso guardado

        progresoTarea = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            barraProgreso.setProgress(barraProgreso.getProgress() + incremento);
            if (barraProgreso.getProgress() >= 1.0) {
                progresoTarea.stop();
                listaTareas.remove(0);
                progresoGuardado = 0; // Resetea el progreso guardado
                if (!listaTareas.isEmpty() && encendida) {
                    iniciarBarraProgreso();
                }
            }
        }));
        progresoTarea.setCycleCount(Timeline.INDEFINITE);
        progresoTarea.play();
    }

    private void iniciarHiloMonitoreo() {
        Thread hiloMonitoreo = new Thread(() -> {
            while (true) {
                if (!listaTareas.isEmpty() && barraProgreso.getProgress() == 0 && encendida) {
                    Platform.runLater(this::iniciarBarraProgreso);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hiloMonitoreo.setDaemon(true);
        hiloMonitoreo.start();
    }
}
