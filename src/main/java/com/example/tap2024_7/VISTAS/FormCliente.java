package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.MODELS.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCliente extends Stage {
    private Scene escena;
    private TextField txtNombre, txtEmail, txtTelefono;
    private Button btnGuardar;
    private VBox vbox;
    private ClienteDAO objCliente;
    private TableView<ClienteDAO> tbvClientes;

    public FormCliente(TableView<ClienteDAO> tbv, ClienteDAO objc) {
        tbvClientes = tbv;
        CrearUI();
        if (objc != null) {
            this.objCliente = objc;
            txtNombre.setText(objCliente.getNomCte());
            txtEmail.setText(objCliente.getEmailCte());
            txtTelefono.setText(objCliente.getTelCte());
            this.setTitle("Editar Cliente");
        } else {
            this.objCliente = new ClienteDAO();
            this.setTitle("Insertar Cliente");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del cliente");
        txtNombre.getStyleClass().add("text-field");

        txtEmail = new TextField();
        txtEmail.setPromptText("Email del cliente");
        txtEmail.getStyleClass().add("text-field");

        txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono del cliente");
        txtTelefono.getStyleClass().add("text-field");

        btnGuardar = new Button("Guardar");
        btnGuardar.getStyleClass().add("button");
        btnGuardar.setOnAction(event -> GuardarCliente());

        vbox = new VBox(txtNombre, txtEmail, txtTelefono, btnGuardar);
        vbox.getStyleClass().add("vbox");

        escena = new Scene(vbox, 400, 300);
        escena.getStylesheets().add(getClass().getResource("/styles/formcliente.css").toExternalForm());
    }

    private void GuardarCliente() {
        objCliente.setEmailCte(txtEmail.getText());
        objCliente.setNomCte(txtNombre.getText());
        objCliente.setTelCte(txtTelefono.getText());
        String msj;
        Alert.AlertType type;

        if (objCliente.getIdCte() > 0) {
            objCliente.UPDATE();
            msj = "Registro actualizado";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objCliente.INSERT() > 0) {
                msj = "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrió un error al insertar";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tbvClientes.setItems(objCliente.SELECTALL());
        tbvClientes.refresh();
        this.close();
    }
}
