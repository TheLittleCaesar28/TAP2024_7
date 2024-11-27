package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserDashboard extends Stage {
    private UsuarioDAO usuario;

    public UserDashboard(UsuarioDAO usuario) {
        this.usuario = usuario;
        this.setTitle("Dashboard - Usuario");

        VBox contenido = CrearContenido();
        contenido.getStyleClass().add("vbox");

        Scene scene = new Scene(contenido, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());

        this.setScene(scene);
        this.show();
    }

    private VBox CrearContenido() {
        Button btnComprar = new Button("Comprar Canciones/Álbumes");
        btnComprar.getStyleClass().add("button");
        btnComprar.setOnAction(event -> new Comprar(usuario.getIdUsuario()));

        Button btnHistorial = new Button("Ver Historial de Compras");
        btnHistorial.getStyleClass().add("button");
        btnHistorial.setOnAction(event -> new HistorialCompras(usuario.getIdUsuario()));

        Button btnMisDatos = new Button("Mis Datos");
        btnMisDatos.getStyleClass().add("button");
        btnMisDatos.setOnAction(event -> new MisDatos(usuario));

        VBox vbox = new VBox(20, btnComprar, btnHistorial, btnMisDatos);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("vbox");
        return vbox;
    }
}
