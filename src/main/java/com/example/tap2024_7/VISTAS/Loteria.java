package com.example.tap2024_7.VISTAS;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.*;

public class Loteria extends Stage {

    private HBox hBoxMain;
    private VBox vbxTablilla, vbxMazo;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private final String[] arImages = {"barril.jpg", "alacran.jpg", "araña.jpg", "balero.jpg", "bandera.jpg", "borracho.jpg", "bota.jpg", "camaron.jpg", "corazon.jpg", "gorrito.jpg", "mano.jpg", "negrito.jpg", "pescado.jpg", "rana.jpg", "soldado.jpg", "valiente.jpg", "catrin.jpg", "botella.jpg", "sol.jpg", "rosa.jpg", "paraguas.jpg"};
    private List<GridPane> plantillas;
    private Queue<String> colaCartas;
    private Set<Button> cartasMarcadas = new HashSet<>();
    private int plantillaActual = 0;
    private Timer timer;
    private int tiempoRestante = 5;
    private String cartaActual;
    private boolean juegoActivo = false;
    private boolean temporizadorIniciado = false;
    private boolean mensajeMostrado = false;

    public Loteria() {
        CrearUI();
        this.setTitle("Loteria Mexicana :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        gdpTablilla = new GridPane();
        CrearTablillas();

        mostrarPlantilla(0);

        Button btnAnterior = new Button("Anterior");
        btnAnterior.setOnAction(e -> mostrarPlantillaAnterior());
        btnAnterior.getStyleClass().add("btn-primary");

        Button btnSiguiente = new Button("Siguiente");
        btnSiguiente.setOnAction(e -> mostrarPlantillaSiguiente());
        btnSiguiente.getStyleClass().add("btn-primary");

        HBox hBoxButtons = new HBox(10, btnAnterior, btnSiguiente);
        hBoxButtons.setPadding(new Insets(10));

        vbxTablilla = new VBox(10, gdpTablilla, hBoxButtons);
        vbxTablilla.setPadding(new Insets(10));

        CrearMazo();

        hBoxMain = new HBox(20, vbxTablilla, vbxMazo);
        hBoxMain.setPadding(new Insets(20));
        escena = new Scene(hBoxMain, 800, 600);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/styles/loteria.css").toExternalForm());
    }

    private void CrearMazo() {
        lblTimer = new Label("00:05");
        lblTimer.getStyleClass().add("lbl-timer");

        imvMazo = new ImageView(new Image(getClass().getResource("/images/dorso.jpeg").toString()));
        imvMazo.setFitHeight(450);
        imvMazo.setFitWidth(300);

        Button btnIniciar = new Button("Iniciar Juego");
        btnIniciar.getStyleClass().add("btn-success");
        btnIniciar.setOnAction(e -> iniciarJuego());

        vbxMazo = new VBox(10, lblTimer, imvMazo, btnIniciar);
        vbxMazo.setPadding(new Insets(10));
    }

    private void CrearTablillas() {
        plantillas = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<String> imagenesRestantes = new ArrayList<>(List.of(arImages));
            GridPane nuevaTablilla = new GridPane();
            nuevaTablilla.setHgap(5);
            nuevaTablilla.setVgap(5);

            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    int index = (int) (Math.random() * imagenesRestantes.size());
                    String imagenSeleccionada = imagenesRestantes.remove(index);

                    Image img = new Image(getClass().getResource("/images/" + imagenSeleccionada).toString());
                    ImageView imv = new ImageView(img);
                    imv.setFitWidth(70);
                    imv.setFitHeight(120);

                    Button btn = new Button();
                    btn.setGraphic(imv);
                    btn.setOnAction(e -> marcarCarta(btn, imagenSeleccionada));
                    nuevaTablilla.add(btn, j, k);
                }
            }
            plantillas.add(nuevaTablilla);
        }
    }

    private void mostrarPlantilla(int index) {
        plantillaActual = index;
        gdpTablilla.getChildren().setAll(plantillas.get(plantillaActual).getChildren());
    }

    private void mostrarPlantillaAnterior() {
        if (juegoActivo) return;
        plantillaActual = (plantillaActual - 1 + plantillas.size()) % plantillas.size();
        mostrarPlantilla(plantillaActual);
    }

    private void mostrarPlantillaSiguiente() {
        if (juegoActivo) return;
        plantillaActual = (plantillaActual + 1) % plantillas.size();
        mostrarPlantilla(plantillaActual);
    }

    private void iniciarJuego() {
        if (timer != null) {
            timer.cancel();
        }
        cartasMarcadas.clear();
        tiempoRestante = 5;
        lblTimer.setText("00:05");
        prepararColaCartas();
        juegoActivo = true;
        temporizadorIniciado = false;
        mensajeMostrado = false;
        cambiarCarta();
    }

    private void prepararColaCartas() {
        List<String> cartas = new ArrayList<>(List.of(arImages));
        Collections.shuffle(cartas);
        colaCartas = new LinkedList<>(cartas);
    }

    private void cambiarCarta() {
        if (colaCartas.isEmpty()) {
            if (!mensajeMostrado) {
                terminarJuego();
                mostrarMensaje("Juego Terminado", "Has perdido.");
                mensajeMostrado = true;
            }
            return;
        }

        cartaActual = colaCartas.poll();
        imvMazo.setImage(new Image(getClass().getResource("/images/" + cartaActual).toString()));

        if (!temporizadorIniciado) {
            temporizadorIniciado = true;
            iniciarTemporizador();
        }
    }


    private void iniciarTemporizador() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!juegoActivo) {
                        timer.cancel();
                    } else if (tiempoRestante > 0) {
                        tiempoRestante--;
                        lblTimer.setText(String.format("00:%02d", tiempoRestante));
                    } else {
                        cambiarCarta();
                        tiempoRestante = 5;
                    }
                });
            }
        }, 0, 1000);
    }

    private void marcarCarta(Button btn, String imagenSeleccionada) {
        if (imagenSeleccionada.equals(cartaActual)) {
            btn.setStyle("-fx-background-color: green;");
            cartasMarcadas.add(btn);

            if (cartasMarcadas.size() == 16 && !mensajeMostrado) {
                mostrarMensaje("¡Ganaste!", "¡Felicidades, has ganado el juego!");
                mensajeMostrado = true;
                terminarJuego();
            }
        }
    }
    private void terminarJuego() {
        if (timer != null) {
            timer.cancel();
        }
        juegoActivo = false;
        temporizadorIniciado = false;
    }


    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
