package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.UsuarioDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CRUDUsuarios extends Stage {

    private TableView<UsuarioDAO> tbvUsuarios;
    private TextField txtNombreUsuario, txtEmail, txtTelefono;
    private PasswordField txtPassword;
    private ComboBox<String> cbTipoUsuario;
    private Button btnAgregar, btnActualizar, btnEliminar;

    public CRUDUsuarios() {
        VBox contenido = CrearContenido();
        Scene scene = new Scene(contenido, 600, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/crudUsuarios.css").toExternalForm());

        this.setTitle("CRUD Usuarios");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        tbvUsuarios = new TableView<>();
        tbvUsuarios.getStyleClass().add("table-view");

        TableColumn<UsuarioDAO, String> colNombre = new TableColumn<>("Usuario");
        colNombre.setCellValueFactory(c -> c.getValue().nombreUsuarioProperty());

        TableColumn<UsuarioDAO, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> c.getValue().emailProperty());

        TableColumn<UsuarioDAO, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(c -> c.getValue().telefonoProperty());

        TableColumn<UsuarioDAO, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(c -> c.getValue().tipoUsuarioProperty());

        tbvUsuarios.getColumns().addAll(colNombre, colEmail, colTelefono, colTipo);
        tbvUsuarios.setItems(new UsuarioDAO().SELECTALL());

        txtNombreUsuario = new TextField();
        txtNombreUsuario.setPromptText("Nombre de Usuario");
        txtNombreUsuario.getStyleClass().add("text-field");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.getStyleClass().add("text-field");

        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("text-field");

        txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.getStyleClass().add("text-field");

        cbTipoUsuario = new ComboBox<>();
        cbTipoUsuario.getItems().addAll("Administrador", "Usuario");
        cbTipoUsuario.setPromptText("Seleccione el Tipo de Usuario");
        cbTipoUsuario.getStyleClass().add("combo-box");

        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> agregarUsuario());
        btnAgregar.getStyleClass().add("button");

        btnActualizar = new Button("Actualizar");
        btnActualizar.setOnAction(e -> actualizarUsuario());
        btnActualizar.getStyleClass().add("button");

        btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(e -> eliminarUsuario());
        btnEliminar.getStyleClass().add("button-danger");
    }

    private VBox CrearContenido() {
        CrearUI();

        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
        botones.getStyleClass().add("hbox");

        VBox vbox = new VBox(10, tbvUsuarios, txtNombreUsuario, txtPassword, txtEmail, txtTelefono, cbTipoUsuario, botones);
        vbox.getStyleClass().add("vbox");

        return vbox;
    }

    private void agregarUsuario() {
        if (txtNombreUsuario.getText().isEmpty() || txtPassword.getText().isEmpty() || cbTipoUsuario.getSelectionModel().isEmpty()) {
            mostrarAlerta("Error", "Por favor, llena todos los campos obligatorios.");
            return;
        }

        UsuarioDAO usuario = new UsuarioDAO();
        usuario.setNombreUsuario(txtNombreUsuario.getText());
        usuario.setPassword(txtPassword.getText());
        usuario.setEmail(txtEmail.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setTipoUsuario(cbTipoUsuario.getSelectionModel().getSelectedItem());
        usuario.INSERT();
        actualizarTabla();
    }

    private void actualizarUsuario() {
        UsuarioDAO seleccionado = tbvUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un usuario para actualizar.");
            return;
        }

        seleccionado.setNombreUsuario(txtNombreUsuario.getText());
        seleccionado.setPassword(txtPassword.getText());
        seleccionado.setEmail(txtEmail.getText());
        seleccionado.setTelefono(txtTelefono.getText());
        seleccionado.setTipoUsuario(cbTipoUsuario.getSelectionModel().getSelectedItem());
        seleccionado.UPDATE();
        actualizarTabla();
    }

    private void eliminarUsuario() {
        UsuarioDAO seleccionado = tbvUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un usuario para eliminar.");
            return;
        }

        seleccionado.DELETE();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tbvUsuarios.setItems(new UsuarioDAO().SELECTALL());
        tbvUsuarios.refresh();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
