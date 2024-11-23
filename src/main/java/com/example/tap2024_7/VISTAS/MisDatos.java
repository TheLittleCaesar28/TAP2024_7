package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.UsuarioDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MisDatos extends Stage {

    private UsuarioDAO usuario;

    public MisDatos(UsuarioDAO usuario) {
        this.usuario = usuario;

        // Crear campos de texto con datos del usuario
        TextField txtNombre = new TextField(usuario.getNombreUsuario());
        txtNombre.setPromptText("Nombre");
        txtNombre.getStyleClass().add("text-field");

        TextField txtEmail = new TextField(usuario.getEmail());
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("text-field");

        TextField txtTelefono = new TextField(usuario.getTelefono());
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.getStyleClass().add("text-field");

        // Botón Guardar
        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.getStyleClass().add("button");
        btnGuardar.setOnAction(event -> {
            usuario.setNombreUsuario(txtNombre.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setTelefono(txtTelefono.getText());
            usuario.UPDATE();
        });

        // Configuración del contenedor VBox
        VBox vbox = new VBox(10, txtNombre, txtEmail, txtTelefono, btnGuardar);
        vbox.setPadding(new Insets(20));
        vbox.getStyleClass().add("vbox");

        // Configuración de la escena
        Scene scene = new Scene(vbox, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());

        this.setScene(scene);
        this.setTitle("Mis Datos Personales");
        this.show();
    }
}
