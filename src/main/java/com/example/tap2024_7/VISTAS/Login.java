package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Stage {

    private TextField txtUsuario;
    private PasswordField txtPassword;
    private Button btnIngresar;
    private VBox root;

    public Login() {
        CrearUI();
        this.setTitle("Login - Spotify");

        // Crear escena y asignar estilos
        Scene scene = new Scene(root, 500, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        // Logo
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/spoti.jpg").toExternalForm()));
        logo.setFitHeight(120);
        logo.setPreserveRatio(true);
        logo.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.3, 0, 0);");

        // Título
        Label title = new Label("Spotify");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox header = new VBox(10, logo, title);
        header.setAlignment(Pos.CENTER);

        // Crear campos de texto
        txtUsuario = new TextField();
        txtUsuario.setPromptText("Nombre de Usuario");
        txtUsuario.getStyleClass().add("text-field");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.getStyleClass().add("password-field");

        // Crear botón de ingreso
        btnIngresar = new Button("Ingresar");
        btnIngresar.getStyleClass().add("button");
        btnIngresar.setOnAction(event -> validarCredenciales());

        // Centrar el botón
        HBox buttonContainer = new HBox(btnIngresar);
        buttonContainer.setAlignment(Pos.CENTER);

        // Configurar GridPane para el formulario
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);
        form.add(txtUsuario, 0, 0);
        form.add(txtPassword, 0, 1);
        form.add(buttonContainer, 0, 2); // Usar el contenedor del botón

        // Estilo del formulario
        StackPane formContainer = new StackPane(form);
        //formContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-border-radius: 15; -fx-padding: 20; -fx-border-color: #1DB954; -fx-border-width: 2;");

        // Contenedor principal
        root = new VBox(40, header, formContainer); // Aumentar espacio entre elementos
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20; -fx-background-color: #191414;");
    }


    private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        UsuarioDAO userDAO = new UsuarioDAO();
        if (userDAO.validarUsuario(usuario, password)) {
            if (userDAO.getTipoUsuario().equals("Administrador")) {
                new AdminDashboard(); // Redirige al dashboard de administrador
            } else {
                new UserDashboard(userDAO); // Redirige al dashboard de usuario
            }
            this.close();
        } else {
            // Mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de autenticación");
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }
}
